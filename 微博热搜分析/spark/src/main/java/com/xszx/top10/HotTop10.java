package com.xszx.top10;

import com.alibaba.fastjson.JSONObject;
import com.xszx.hbase.HbaseConnect;
import com.xszx.hbase.HbaseUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.List;

public class HotTop10 {
    public static void main(String[] arg) {
        SparkConf conf = new SparkConf();
        conf.setAppName("top10_hot");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> textFileRDD = sc.textFile(arg[0]);
        JavaPairRDD<Tuple2<String, String>, Integer> transformRDD = textFileRDD.mapToPair(
                new PairFunction<String, Tuple2<String, String>, Integer>() {
                    @Override
                    public Tuple2<Tuple2<String, String>, Integer> call(String s) throws Exception {
                        JSONObject json = JSONObject.parseObject(s);
                        String weibo_id = json.getString("weibo_id");
                        String event_type = json.getString("event_type");
                        return new Tuple2<>(
                                new Tuple2<>(weibo_id, event_type),
                                new Integer(1));
                    }
                });
        JavaPairRDD<Tuple2<String, String>, Integer> aggRDD = transformRDD.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer1, Integer integer2) throws Exception {
                        return integer1 + integer2;
                    }
                });
        JavaPairRDD<String, Integer> getViewWeiboRDD = aggRDD.filter(new Function<Tuple2<Tuple2<String, String>, Integer>, Boolean>() {
            @Override
            public Boolean call(Tuple2<Tuple2<String, String>, Integer> tuple2) throws Exception {
                String action = tuple2._1._2;
                return action.equals("view");
            }
        }).mapToPair(
                new PairFunction<Tuple2<Tuple2<String, String>, Integer>, String, Integer>() {
                    @Override
                    public Tuple2<String, Integer> call(Tuple2<Tuple2<String, String>, Integer> tuple2) throws Exception {
                        return new Tuple2<>(tuple2._1._1, tuple2._2);//微博id，浏览数量
                    }
                });
        JavaPairRDD<String, Integer> getShareWeiboRDD = aggRDD.filter(new Function<Tuple2<Tuple2<String, String>, Integer>, Boolean>() {
            @Override
            public Boolean call(Tuple2<Tuple2<String, String>, Integer> tuple2) throws Exception {
                String action = tuple2._1._2;
                return action.equals("share");
            }
        }).mapToPair(new PairFunction<Tuple2<Tuple2<String, String>, Integer>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Tuple2<String, String>, Integer> tuple2) throws Exception {
                return new Tuple2<>(tuple2._1._1, tuple2._2); //微博id，转发数量

            }
        });
        JavaPairRDD<String, Integer> getCommitWeiboRDD = aggRDD.filter(new Function<Tuple2<Tuple2<String, String>, Integer>, Boolean>() {
            @Override
            public Boolean call(Tuple2<Tuple2<String, String>, Integer> tuple2) throws Exception {
                String action = tuple2._1._2;
                return action.equals("commit");
            }
        }).mapToPair(new PairFunction<Tuple2<Tuple2<String, String>, Integer>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Tuple2<String, String>, Integer> tuple2) throws Exception {
                return new Tuple2<>(tuple2._1._1, tuple2._2);//微博id，评论数量
            }
        });
        //关联
        JavaPairRDD<String, Tuple2<Integer, Optional<Integer>>>
                tmpJoinWeiboRDD = getViewWeiboRDD.leftOuterJoin(getShareWeiboRDD);
        JavaPairRDD<String, Tuple2<Tuple2<Integer, Optional<Integer>>, Optional<Integer>>>
                joinWeiboRDD = tmpJoinWeiboRDD.leftOuterJoin(getCommitWeiboRDD);
        JavaPairRDD<WeiboSortKey, String> transWeiboRDD = joinWeiboRDD.mapToPair(new PairFunction<Tuple2<String, Tuple2<Tuple2<Integer, Optional<Integer>>, Optional<Integer>>>, WeiboSortKey, String>() {
            @Override
            public Tuple2<WeiboSortKey, String> call(Tuple2<String, Tuple2<Tuple2<Integer, Optional<Integer>>, Optional<Integer>>> tuple2) throws Exception {
                String weibo_id = tuple2._1;
                int viewcount = tuple2._2._1._1;
                int cartcount = 0;
                int purchasecount = 0;
                if (tuple2._2._1._2.isPresent()) {
                    cartcount = tuple2._2._1._2.get().intValue();
                }
                if (tuple2._2._2.isPresent()) {
                    purchasecount = tuple2._2._2.get().intValue();
                }
                WeiboSortKey sortKey = new WeiboSortKey(viewcount, cartcount, purchasecount);
                return new Tuple2<>(sortKey, weibo_id);
            }
        });
        JavaPairRDD<WeiboSortKey, String> sortedWeiboRDD = transWeiboRDD.sortByKey(false);
        List<Tuple2<WeiboSortKey, String>> top10HotList = sortedWeiboRDD.take(10);
        //调用top10ToHbase方法
        try {
            top10ToHbase(top10HotList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭数据库连接
        HbaseConnect.closeConnection();
        sc.close();
    }

    public static void top10ToHbase(List<Tuple2<WeiboSortKey, String>> top10WeiboList) throws Exception {
        HbaseUtils.createTable("top10", "top10_weibo");
        String[] column = {"weibo_id", "viewCount", "shareCount", "commitCount"};
        String viewcount = "";
        String shareCount = "";
        String commitCount = "";
        String weibo_id = "";
        int count = 0;
        for (Tuple2<WeiboSortKey, String> top10 : top10WeiboList) {
            count++;
            viewcount = String.valueOf(top10._1.getViewCount());
            shareCount = String.valueOf(top10._1.getShareCount());
            commitCount = String.valueOf(top10._1.getCommitCount());
            weibo_id = top10._2;
            String[] value = {weibo_id, viewcount, shareCount, commitCount};
            HbaseUtils.putsToHBase("top10",
                    "rowkey_top" + count,
                    "top10_weibo",
                    column,
                    value);
        }
    }
}
