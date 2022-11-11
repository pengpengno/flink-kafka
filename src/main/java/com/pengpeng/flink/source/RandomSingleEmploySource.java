package com.pengpeng.flink.source;

import com.pengpeng.flink.source.test.random.DataRandom;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
@Slf4j
public class RandomSingleEmploySource extends RichSourceFunction<String> {

    private boolean isRunning = true;

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (isRunning){
            String randomJsonData = DataRandom.get1RandomJsonData();
            log.info("生成了一条员工数据新数据 {}",randomJsonData);
            ctx.collect(randomJsonData);
            Thread.sleep(500);
        }
    }


    @Override
    public void cancel() {
        isRunning =false;
    }
}
