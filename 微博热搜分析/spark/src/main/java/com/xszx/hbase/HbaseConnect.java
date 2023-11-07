package com.xszx.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HbaseConnect {
    //声明静态变量
    public static Configuration conf;//识别Hadoop配置
    public static Connection conn;//建立连接
    public static HBaseAdmin hBaseAdmin;//连接之后的账号

    //单例
    static {
        conf = HBaseConfiguration.create();//创建配置实例对象
        //设置hbase的连接地址和端口
        conf.set("hbase.zookeeper.quorum", "spark01,spark02,spark03");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取Hbase的Admin账号
    public static HBaseAdmin getHBaseAdmin() {
        try {
            hBaseAdmin = (HBaseAdmin) conn.getAdmin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hBaseAdmin;
    }

    public static Connection getConnection() {
        return conn;
    }

    //关闭hbase连接
    public static synchronized void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
