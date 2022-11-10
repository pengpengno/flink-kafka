package com.pengpeng.flink.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2022/8/2 16:49
 */
@Data
public class DataGeneralFieldRelation implements Serializable {

//    @ApiModelProperty("模版code")
    private String templateCode;

//    @ApiModelProperty("模版name")
    private String templateName;

//    @ApiModelProperty("实例code")
    private String realityCode;

//    @ApiModelProperty("实例name")
    private String realityName;
}