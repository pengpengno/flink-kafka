import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;


@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class KafkaTest {


    @Test
    public void kafkaProducer() throws Exception {
        Properties properties = new Properties();
//        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String broker = JDBCTestBase.kafka_hosts;
        System.out.println("broker:" + broker);
        String topic = JDBCTestBase.kafka_topic;
        properties.setProperty("bootstrap.servers",broker);

        String message = "wangpeng";
        FlinkKafkaProducer flinkKafkaProducer011 = new FlinkKafkaProducer(topic,new SimpleStringSchema(),properties);
        FlinkKafkaProducer flinkKafkaProducer0112 = new FlinkKafkaProducer(topic,new SimpleStringSchema(),properties,null);
        DataStreamSource<String> text = env.addSource(new MyNoParalleSource(message));
        text.addSink(flinkKafkaProducer011);
        env.execute("sssss");

    }
    @Test
    /**
     * 新版本kafka数据推送
     */
    public void flinkKafkaProcu() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String topic = JDBCTestBase.kafka_topic;
        String broker = JDBCTestBase.kafka_hosts;
        StopWatch stopWatch = new StopWatch();
        List<String> messages  =CollectionUtil.newArrayList();
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

    }


    @Test
    /**
     * kafka flink 消费
     */
    public void flinkKafkaConsumer() throws Exception {
        String broker = JDBCTestBase.kafka_hosts;
        KafkaSource<String> build = KafkaSource.<String>builder()
                .setTopics(JDBCTestBase.kafka_topic)
                .setBootstrapServers(broker)
//                .setGroupId("my-group")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
//        new RideCleansingSolution.NYCFilter()
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataSource = env.fromSource(build, WatermarkStrategy.noWatermarks(), "ka");
        DataStream<String> result = dataSource.map(new MapFunction<String,String>() {
            @Override
            public String map(String value) throws Exception {
                System.out.println("当前线程id：" + Thread.currentThread().getId() + ",value: " + value);
                return value;
            }
        });
        result.print().setParallelism(1);
        env.execute("dothis");
    }
    public static class MyNoParalleSource implements SourceFunction<String> {
        String message;
        public MyNoParalleSource(){

        }

        public MyNoParalleSource(String message) {
            this.message = message;
        }

        @Override
        public void run(SourceContext<String> sourceContext) throws Exception {
            sourceContext.collect(this.message);
        }

        @Override
        public void cancel() {

        }
    }
}
