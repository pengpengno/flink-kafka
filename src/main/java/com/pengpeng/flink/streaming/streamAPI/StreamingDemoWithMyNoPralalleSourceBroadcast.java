package com.pengpeng.flink.streaming.streamAPI;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.pengpeng.flink.source.RandomSingleEmploySource;
import com.pengpeng.flink.source.test.pojo.Employee;
import com.pengpeng.flink.streaming.custormSource.MyNoParalleSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 *  broadcast分区规则
 */
@Slf4j
public class StreamingDemoWithMyNoPralalleSourceBroadcast {

    public static void main(String[] args) throws Exception {
        //获取Flink的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //获取数据源
        DataStreamSource<String> text = env.addSource(new RandomSingleEmploySource()).setParallelism(1);//注意：针对此source，并行度只能设置为1

        DataStream<String> num = text.broadcast().map((MapFunction<String, String>) value -> {
            Thread curThread = Thread.currentThread();
            log.info("线程 {} id：{},接收到数据：{}" ,Thread.currentThread().getName(),curThread.getId(), value);
            return value;
        });
//        num.assignTimestampsAndWatermarks(WatermarkStrategy.forGenerator(new WatermarkStrategy<String>() {
//            @Override
//            public WatermarkGenerator<String> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
//                MetricGroup metricGroup = context.getMetricGroup();
//                return metricGroup.;
//                return String.valueOf(IdUtil.getSnowflake(1l));
//            }
//        }))
//        num.assignTimestampsAndWatermarks(WatermarkStrategy.noWatermarks());
        SingleOutputStreamOperator<String> reduce = num.keyBy((KeySelector<String, String>) value -> {
            try {
                Employee employee = JSONObject.parseObject(value, Employee.class);
                return employee.getUserName();
            } catch (Exception ex) {
                return "null";
            }
        }).window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .reduce((ReduceFunction<String>) (value1, value2) -> value1 + value2);

//        window.process(new ProcessWindowFunction<String, Object, String, TimeWindow>() {
//            @Override
//            public void process(String s, Context context, Iterable<String> elements, Collector<Object> out) throws Exception {
//                for (String string: elements) {
//                    out.collect(string);
//                }
//                System.out.println(s);
//            }
//        });
        //每2秒钟处理一次数据
//        DataStream<String> sum = num.timeWindowAll(Time.seconds(2)).sum(0);

        //打印结果
//        num.print().setParallelism(1);
//        num.print();
        reduce.print();

        String jobName = StreamingDemoWithMyNoPralalleSourceBroadcast.class.getSimpleName();
        env.execute(jobName);
    }
}
