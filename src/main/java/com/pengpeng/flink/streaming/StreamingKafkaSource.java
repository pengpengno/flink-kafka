package com.pengpeng.flink.streaming;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.util.Properties;

/**
 * kafkaSource
 *
 * Created by xuwei.tech on 2018/10/23.
 */
public class StreamingKafkaSource {

    public static void main(String[] args) throws Exception {
        //获取Flink的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //checkpoint配置
        env.enableCheckpointing(5000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        //设置statebackend

        //env.setStateBackend(new RocksDBStateBackend("hdfs://hadoop100:9000/flink/checkpoints",true));

//        prop.setProperty("group.id","con1");
        String broker = "localhost:9092";
//        KafkaSource<String> myConsumer = new KafkaSource(topic, new SimpleStringSchema(), prop);
        KafkaSource<String> myConsumer = KafkaSource.<String>builder()
                .setTopics("quickstart-events")
                .setBootstrapServers(broker)
//                .setGroupId("my-group")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
//        myConsumer.setStartFromGroupOffsets();//默认消费策略

        DataStreamSource<String> text = env.fromSource(myConsumer, WatermarkStrategy.noWatermarks(),"kafka");

        text.print().setParallelism(1);

        env.execute("StreamingFromCollection");


    }
}
