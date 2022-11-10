import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.RichCoMapFunction;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class FlinkTest {


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
        ArrayList<Integer> integer2 = CollectionUtil.newArrayList(3,2,4,7,6,6,-2);
//        DataStream<Integer> source =
        DataStreamSource<Integer> dataStreamSource = environment.fromCollection(integers);
        dataStreamSource.setParallelism(1);
        dataStreamSource.partitionCustom(new MyPartition(), new KeySelector<Integer, Long>() {
            @Override
            public Long getKey(Integer value) throws Exception {
                return 1l;
            }
        });


        DataStreamSource<Integer> dataStreamSource2 = environment.fromCollection(integer2);
        SingleOutputStreamOperator<Object> map = dataStreamSource2.connect(dataStreamSource2)
                .map(new RichCoMapFunction<Integer, Integer, Object>() {
            @Override
            public Object map1(Integer value) throws Exception {

                return value + 1;
            }

            @Override
            public Object map2(Integer value) throws Exception {
                return 0;
            }
        });
//        DataSet<Integer> dataStreamSource = environment.fromElements(1, 2, 3, 4, 5);
        SingleOutputStreamOperator<Integer> filter = dataStreamSource.filter((FilterFunction<Integer>) string -> {
            return string > 3;
        });

        filter.print("filterMap");
        filter.print("filterMap");
        filter.addSink(
                StreamingFileSink.forRowFormat
                        (new Path("C:\\wangpeng\\peng\\flink-kafka\\src\\test\\sink1"),new SimpleStringEncoder()).build());
        map.addSink(
                StreamingFileSink.forRowFormat
                        (new Path("C:\\wangpeng\\peng\\flink-kafka\\src\\test\\sink2"),new SimpleStringEncoder()).build());
        environment.execute("filter Job");
//        new StreamingFileSink.BucketsBuilder<x>()
//        filter.writeAsCsv("C://data");
    }

     static class MyPartition implements Partitioner<Long> {
        @Override
        public int partition(Long key, int numPartitions) {
            System.out.println("分区总数："+numPartitions);
            if(key % 2 == 0){
                return 0;
            }else{
                return 1;
            }
        }
    }
}
