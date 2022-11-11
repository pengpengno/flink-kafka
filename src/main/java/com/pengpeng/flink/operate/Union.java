package com.pengpeng.flink.operate;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

public class Union extends RichMapFunction<String,String> {

    @Override
    public String map(String value) throws Exception {
        return null;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}
