package com.uce.plataforms;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class SpotifyYoutubeFilter {

    public static class ViewMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] campos = value.toString().split(",");

            if (campos.length > 6) {
                try {
                    long views = Long.parseLong(campos[6].replaceAll("\"", "").trim()); // Columna "views"
                    if (views > 1000000) {
                        context.write(new Text(value.toString()), NullWritable.get());
                    }
                } catch (NumberFormatException e) {
                    // ignora encabezados u errores
                }
            }
        }
    }

    public static class ViewReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
        public void reduce(Text key, Iterable<NullWritable> values, Context context)
                throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }
}
