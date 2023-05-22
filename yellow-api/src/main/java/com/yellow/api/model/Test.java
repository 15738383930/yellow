package com.yellow.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stars.datachange.annotation.ChangeModel;
import com.stars.datachange.annotation.ChangeModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@ChangeModel(source = ChangeModel.Source.DB, modelName = "girlfriend")
public class Test {

    @ChangeModelProperty(value = "姓名", skipComparison = true)
    private String name;

    @ChangeModelProperty
    private Integer type;

    @ChangeModelProperty("交往时间")
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date socialTime;

    @ChangeModelProperty(split = true)
    private String favoriteFood;

    @ChangeModelProperty(value = "照片", ignore = true)
    private List<String> photo;

    @ChangeModelProperty(bitOperation = true)
    private Integer touristPlace;

    private String touristPlaceText;

    private String typeText;
}