package com.itheima.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {
    /**
     * 相当于yarn集群的客户端
     * 需要在此封装我们的mr程序的相关运行参数,指定jar包最后提交给yarn
     **/
    public static void main(String[] args)throws Exception{

        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf,"word count");
        //设置环境参数
        job.setJarByClass(WordCount.class);
        //指定本业务job要使用的mapper/reduce业务类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        //指定mapper输出数据的key和values类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //指定最终输出数据的key和values类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //指定job输出的原始文件的所在目录
        //设置输入文件
        //FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileInputFormat.setInputPaths(job,"hdfs://192.168.225.128:9000/input/");
        //设置输出文件
        FileOutputFormat.setOutputPath(job,new Path("hdfs://192.168.225.128:9000/output/"));
        //FileOutputFormat.setOutputPath(job,new Path(args[1]));

        System.exit(job.waitForCompletion(true)?0:1);
        //job.waitForCompletion(true);
    }
}
