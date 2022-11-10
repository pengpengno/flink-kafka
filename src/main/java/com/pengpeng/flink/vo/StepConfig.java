package com.pengpeng.flink.vo;

import com.alibaba.fastjson2.JSONObject;
import com.pengpeng.flink.enums.OperatorTypeEnum;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2022/4/1 14:50
 */
@Data
public class StepConfig implements Serializable {

//    @ApiModelProperty("步骤ID")
    private Long opId;
    /**
     * @see OperatorTypeEnum
     */
//    @ApiModelProperty("步骤类型")
    private Integer opType;

//    @ApiModelProperty("步骤名称")
    private String opName;

//    @ApiModelProperty("输入数据集合")
    private List<Long> datas;

//    @ApiModelProperty("配置")
    private JSONObject config;




}
