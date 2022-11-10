package com.pengpeng.flink.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2022/8/2 16:49
 */
@Data
public class DataGeneralFormRelation implements Serializable {

//    @ApiModelProperty("模版code")
    private Long templateId;

//    @ApiModelProperty("模版name")
    private String templateName;

//    @ApiModelProperty("实例code")
    private Long realityId;

//    @ApiModelProperty("实例name")
    private String realityName;

//    @ApiModelProperty("字段映射关系")
    private List<DataGeneralFieldRelation> fieldRelations;
}

