import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scala.jdk.FunctionWrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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

//        env.addS
    }



    @Test
    public void flinkKafkaConsumer(){
        String broker = JDBCTestBase.kafka_hosts;

        KafkaSource<String> build = KafkaSource.<String>builder()
                .setTopics(JDBCTestBase.kafka_topic)
                .setBootstrapServers(broker)
                .setGroupId("my-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromSource(build, WatermarkStrategy.noWatermarks(),"ka");
//        env.execute("sss");
//        env.

    }

    @Test
    public void testFilter() throws Exception {
        List<String> people = new ArrayList<String>();

        people.add("Swim");
        people.add("peter");
        people.add("Pebbles");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> flintstones = env.fromCollection(people);
        flintstones.filter((FilterFunction<String>) s -> {
            return s.contains("S");
        });

//        flintstones.executeAndCollect();
        flintstones.print("flwork");

        flintstones.addSink(
                StreamingFileSink.forRowFormat
                        (new Path("C://data/sink2"),new SimpleStringEncoder()).build());
        env.execute("dowork");



    }
    @Test
    public void filter () throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        //        environment.setParallelism(1);
//        ArrayList<String> integers = CollectionUtil.newArrayList("yyy sdausd dasiod ","rwerrrr","lllll");
        ArrayList<Integer> integers = CollectionUtil.newArrayList(1,2,4,5,6,6,-2);
//        DataStream<Integer> source =

        DataStreamSource<Integer> dataStreamSource = environment.fromCollection(integers);
//        DataSet<Integer> dataStreamSource = environment.fromElements(1, 2, 3, 4, 5);
        SingleOutputStreamOperator<Integer> filter = dataStreamSource.filter((FilterFunction<Integer>) string -> {
            return string > 3;
//                if (strin g.contains("y")){
//                    return false;
//                }return true;
        });
//        dataStreamSource.printOnTaskManager("filterMap");
        filter.print("filterMap");
        filter.addSink(
                StreamingFileSink.forRowFormat
                        (new Path("C://data/sink.txt"),new SimpleStringEncoder()).build());
        environment.execute("filter Job");
//        new StreamingFileSink.BucketsBuilder<x>()
//        filter.writeAsCsv("C://data");
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
