package com.itheima.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * KEYIN:默认情况下，是MapReduce所读到的一行文本的其实偏移量：Long,
 * 但是在hadoop中有自己的更精简的序列化接口,所以不直接用Long,而用LongWritable
 * VALUEOUT:是用户自定义逻辑化处理完成之后输出数据的value:在此处是单词次数:Integer
 **/


public class WordCountMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
    /**
     * map阶段的业务逻辑就写在自定义的map()方法中
     * maptask会对每一个输入数据调用一次我们自定义的map()方法
     **/
    public void map(LongWritable key,Text value,Context context)
            throws IOException,InterruptedException{

        String line=value.toString();
        String[] words=line.split(" ");

        for(String word:words){
            context.write(new Text(word),new IntWritable(1));
        }

    }

}
