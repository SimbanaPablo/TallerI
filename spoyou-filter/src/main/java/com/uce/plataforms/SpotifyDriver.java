package com.uce.plataforms;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/*
 * Filtrar las canciones donde el valor de "youtube_views" sea mayor a 1000000.
 * Filtrar registros con más de 1,000,000 views en YouTube.
 */
public class SpotifyDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Spotify YouTube Views Filter");

        job.setJarByClass(SpotifyDriver.class);
        job.setMapperClass(SpotifyYoutubeFilter.ViewMapper.class);
        job.setReducerClass(SpotifyYoutubeFilter.ViewReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
