package com.itheima.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
    /**
     *<angelaba,1><angelaba,1><angelaba,1><angelaba,1>
     *<banana,1><banana,1><banana,1><banana,1>
     *<hello,1><hello,1><hello,1><hello,1>
     *入参key,是一组相同单词kv对的key
     **/
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException,InterruptedException{
        int count=0;
//              迭代器写法
//              Iterator<IntWritable>iterator=values.iterator();
//              while(iterator.hasNext())
//              {
//                  count+=iterator.next().get();
//              }
        for(IntWritable value:values){
            count+=value.get();
        }
        context.write(key,new IntWritable(count));
    }
}
