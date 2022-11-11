import cn.hutool.core.date.StopWatch;
import com.pengpeng.flink.sink.FlinkKafkaSink;
import com.pengpeng.flink.source.RandomSingleEmploySource;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
/**
 * 基本算子操作测试
 */
public class OperateTest {




    @Test
    /**
     * 生成数据源测试
     */
    public void newSourceTest() throws Exception {

        StopWatch stopWatch = new StopWatch();

        StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> randomSource = streamEnv.addSource(new RandomSingleEmploySource());

        KafkaSink<String> kafkaSink = FlinkKafkaSink.out2KafkaSink();

        randomSource.sinkTo(kafkaSink).name("sinkName");

        streamEnv.execute("数据流执行");

        log.info("{}执行耗时{}ms  数据大小 ",stopWatch.getLastTaskName(),stopWatch.getLastTaskTimeMillis());

    }


    @Test
    public void unionTest(){

    }
}
