package com.pengpeng.flink.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2022/4/15 15:38
 */
@Data
public class CalcStepVp implements Serializable {
//    @ApiModelProperty("步骤id")
    private Long Id;

//    @ApiModelProperty("输出")
    private List<Long> output;

//    @ApiModelProperty("计算步骤")
    private List<StepConfig> steps;
}