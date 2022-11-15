package com.pengpeng.flink.streaming;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * kafkaSink
 *
 * Created by xuwei.tech on 2018/10/23.
 */
@Slf4j
public class StreamingKafkaSink {

    public static void main(String[] args) throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String topic = "quickstart-events";
        String broker = "localhost:9092";
        StopWatch stopWatch = new StopWatch();
        List<String> messages  = CollectionUtil.newArrayList();
        Integer times = 1000000;
        for (int i = 0; i < times; i++) {
            messages.add("times"+i + "");
        }
        int length = messages.toString().getBytes(StandardCharsets.UTF_8).length;
        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers(broker)
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(topic)
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build()
                ).build();
        stopWatch.start("开始"+times+"数据传输");
        DataStreamSource<String> text = env.fromCollection(messages);
        text.sinkTo(kafkaSink).name("sink");
        env.execute(" trySink");
        stopWatch.stop();
        log.info("{}执行耗时{}ms  数据大小 {}M",stopWatch.getLastTaskName(),stopWatch.getLastTaskTimeMillis(),length/1024/1024);


//
//        //获取Flink的运行环境
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//
//        //checkpoint配置
//        env.enableCheckpointing(5000);
//        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
//        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
//        env.getCheckpointConfig().setCheckpointTimeout(60000);
//        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
//        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
//
//        //设置statebackend
//
//        //env.setStateBackend(new RocksDBStateBackend("hdfs://hadoop100:9000/flink/checkpoints",true));
//
//
//        DataStreamSource<String> text = env.socketTextStream("hadoop100", 9001, "\n");
//
//        String brokerList = "localhost:9092";
//        String topic = "quickstart-events";
//
//        Properties prop = new Properties();
//        prop.setProperty("bootstrap.servers",brokerList);
//
//        //第一种解决方案，设置FlinkKafkaProducer011里面的事务超时时间
//        //设置事务超时时间
//        //prop.setProperty("transaction.timeout.ms",60000*15+"");
//
//        //第二种解决方案，设置kafka的最大事务超时时间
//
//        //FlinkKafkaProducer011<String> myProducer = new FlinkKafkaProducer011<>(brokerList, topic, new SimpleStringSchema());
//
//        //使用仅一次语义的kafkaProducer
//
//        Kaf<String> myProducer = new FlinkKafkaProducer011<>(topic, new KeyedSerializationSchemaWrapper<String>(new SimpleStringSchema()), prop, FlinkKafkaProducer011.Semantic.EXACTLY_ONCE);
//        text.addSink(myProducer);
//
//
//        env.execute("StreamingFromCollection");


    }
}
