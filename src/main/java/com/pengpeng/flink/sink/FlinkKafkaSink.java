package com.pengpeng.flink.sink;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import com.pengpeng.flink.properties.ConfigConsts;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class FlinkKafkaSink {

    public static KafkaSink<String> out2KafkaSink(){
        String topic = ConfigConsts.kafka_topic;
        String broker = ConfigConsts.kafka_hosts;
//        StopWatch stopWatch = new StopWatch();
//        List<String> messages  = CollectionUtil.newArrayList();
//        Integer times = 1000000;
//        for (int i = 0; i < times; i++) {
//            messages.add("times"+i + "");
//        }
//        int length = messages.toString().getBytes(StandardCharsets.UTF_8).length;
        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers(broker)
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(topic)
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build()
                ).build();
        return kafkaSink;
    }
}
