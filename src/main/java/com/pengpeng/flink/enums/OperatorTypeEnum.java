package com.pengpeng.flink.enums;


/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2022/6/29 10:55
 */
public enum OperatorTypeEnum {

    //虚表相关
    FILTER(1,  "filter", "过滤"), //
    ADD(2,  "addColumn", "新增列"), //
    PIVOT(3,  "pivot", "透视汇总"), //
    UNION(4,  "union", "合并行"), //
    JOIN(5,  "join", "合并列"), //
    COLUMN(6,  "column", "设置字段"), //

    FORM_DATA(7,  "formData", "表数据源"), //
    AGG(8,  "agg", "聚合"), //
    MERGE(9,  "merge", "合并列MERGE"), //
    UNPIVOT(10,  "unpivot", "反透视"), //
    ORDER(11,  "order", "排序"),//
    RENAME(12, "rename", "重命名"),//
    CALC(13,"calc","公式列"),//

    //表单转换
    JSON_CONVERSION_2TABLE(14, "JsonConversion2Table", "json转表格"),//

    TABLE_CONVERSION_2JSON(15, "TableConversion2Json", "表格转json"),//

    JSON_DATA(16, "JsonData", "json数据"),//

    ;

    private Integer opTypeId;
    private String opType;
    private String name;

    OperatorTypeEnum(Integer nodeTypeId, String opType, String name) {
        this.opTypeId = nodeTypeId;

        this.opType = opType;
        this.name = name;
    }

    public static OperatorTypeEnum getById(Integer id) {
        for (OperatorTypeEnum operatorTypeEnum : values()) {
            if (operatorTypeEnum.opTypeId.equals(id)) {
                // 获取指定的枚举
                return operatorTypeEnum;
            }
        }
        return FILTER;
    }

    public Integer getOpType() {
        return opTypeId;
    }

    public Integer getNodeTypeId() {
        return opTypeId;
    }


    public String getName() {
        return name;
    }



}

