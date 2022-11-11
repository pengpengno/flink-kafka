package com.pengpeng.flink.source.test.pojo;

import lombok.Data;

import java.io.Serializable;
@Data
public class Employee implements Serializable {

    private String userName;

    private String area;

    private Long salary;

    private String time;
}
