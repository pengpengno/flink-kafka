package com.pengpeng.flink.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
/**
 * 运算结果数据 存储  后续设计可以处理
 * @see SalaryReqs
 */
public class CalV2Snap implements Serializable {

//    @ApiModelProperty("JSON数据对象")
    private String json;

//    @ApiModelProperty("数据对象")
    private List<String> data;

//    @ApiModelProperty("字段详细信息")
//    private FormGeneralConfig form;

//    @ApiModelProperty("切片类型")
//    /**
//     * @see CalDataTypeEnum
//     */
    private String shotJsonType;


//    @Override
    public CalV2Snap dataToGenSnap() {
        return this;
    }
}
