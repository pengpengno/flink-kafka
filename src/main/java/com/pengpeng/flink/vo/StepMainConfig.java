package com.pengpeng.flink.vo;



import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2022/4/1 15:37
 */
@Data
public class StepMainConfig implements Serializable {
//    @ApiModelProperty("模版id")
    private Long id;

//    @ApiModelProperty("模版名字")
    private  String name;

//    @ApiModelProperty("缩略图url")
    private  String snapshotUrl;

//    @ApiModelProperty("表映射关系")
    private List<DataGeneralFormRelation> dataSourcePrepare;

//    @ApiModelProperty("数据源/多种数据结构")
    private List<GenericDataObject> dataSource;

//    @ApiModelProperty("输出")
    private List<GenericDataObject> output;

//    @ApiModelProperty("计算步骤")
    private CalcStepVp step;

//    @ApiModelProperty("变量列表Json")
    private String paramJson;


}
