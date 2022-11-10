package com.pengpeng.flink.vo;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2022/7/4 15:58
 */
@Data
//public class GenericDataObject implements Serializable, SnapDataAction,Cloneable {
public class GenericDataObject implements Serializable {

//    @ApiModelProperty("id")
    private Long id;

//    @ApiModelProperty("name")
    private String name;

//    @ApiModelProperty("数据类型")
//    /**
//     * @see DataStructureType
//     */
    private Integer type;

//    @ApiModelProperty("表头")
    private Header form;

//    @ApiModelProperty("数据")
    private List<String> data;

//    @ApiModelProperty("JSON")
    private String json;

//    @ApiModelProperty("其他")
    private String extra;

//    @Override
//    public CalV2Snap dataToGenSnap() {
//        CalV2Snap calV2Snap = new CalV2Snap();
//        if (ObjectUtil.isNotNull(json) && StrUtil.isNotBlank(json)){
//            calV2Snap.setJson(json);
//            calV2Snap.setShotJsonType(CalDataTypeEnum.JSON_TABLE.name());
//        }
//        else {
//            calV2Snap.setData(data);
//            calV2Snap.setForm(form);
//            calV2Snap.setShotJsonType(CalDataTypeEnum.GEN_FORM.name());  // 通常情况下 JSON_TABLE 也是二维表数据 目前存储的业务逻辑不做区分
//        }
//        return calV2Snap;
//    }
//    public CalV2Snap dataToGenSnap(CalDataTypeEnum dataTypeEnum) {
//        CalV2Snap calV2Snap = new CalV2Snap();
//        if (ObjectUtil.isNotNull(json) && StrUtil.isNotBlank(json)){
//            calV2Snap.setJson(json);
//            calV2Snap.setShotJsonType(dataTypeEnum.name());
//        }
//        else {
//            calV2Snap.setData(data);
//            calV2Snap.setForm(form);
//            calV2Snap.setShotJsonType(dataTypeEnum.name());  // 通常情况下 JSON_TABLE 也是二维表数据 目前存储的业务逻辑不做区分
//        }
//        return calV2Snap;
//    }
//    public GenericDataObject clone() {
//        try {
//            return (GenericDataObject) super.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
