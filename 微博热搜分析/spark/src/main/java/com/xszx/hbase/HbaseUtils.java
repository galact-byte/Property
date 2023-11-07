package com.xszx.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HbaseUtils {
    public static void createTable(String tableName, String... columFamilys)
            throws IOException {
        HBaseAdmin admin = HbaseConnect.getHBaseAdmin();
        //判断表是否存在
        if (admin.tableExists(TableName.valueOf(tableName))) {
            //关闭表
            admin.disableTable(TableName.valueOf(tableName));
            //删除表
            admin.deleteTable(TableName.valueOf(tableName));
        }
        TableDescriptorBuilder hdb = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName));

        for (String cf : columFamilys) {
            ColumnFamilyDescriptor of1 = ColumnFamilyDescriptorBuilder.of(cf);
            hdb.setColumnFamily(of1);
        }
        //构建
        TableDescriptor build = hdb.build();
        admin.createTable(build);
        admin.close();
    }

    public static void putsToHBase(String tableName,
                                   String rowkey,
                                   String cf,
                                   String[] column,
                                   String[] value)
            throws Exception {
        Table table = HbaseConnect.getConnection().getTable(TableName.valueOf(tableName));
        Put puts = new Put(rowkey.getBytes());
        for (int i = 0; i < column.length; i++) {
            puts.addColumn(
                    Bytes.toBytes(cf),
                    Bytes.toBytes(column[i]),
                    Bytes.toBytes(value[i]));
        }
        table.put(puts);
        table.close();
    }

    public static ResultScanner scan(String tableName)
            throws IOException {
        //获取指定HBase数据表的操作对象
        Table table = HbaseConnect.getConnection().getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        return table.getScanner(scan);
    }

    public static void putsOneToHBase(String tableName,
                                      String rowkey,
                                      String cf,
                                      String column,
                                      String value)
            throws IOException {
        Table table = HbaseConnect.getConnection().getTable(TableName.valueOf(tableName));
        Put puts = new Put(rowkey.getBytes());
        puts.addColumn(
                Bytes.toBytes(cf),
                Bytes.toBytes(column),
                Bytes.toBytes(value));
        table.put(puts);
        table.close();
    }
}
