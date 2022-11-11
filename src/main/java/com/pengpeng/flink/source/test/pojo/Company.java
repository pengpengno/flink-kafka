package com.pengpeng.flink.source.test.pojo;

import lombok.Data;

import java.util.List;

/**
 * 公司结构
 */
@Data
public class Company {

    private String companyName;  // 公司名称

    private List<Employee> employees;  // 员工列表
}
