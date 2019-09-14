package com.algaworks.Util;

import org.springframework.beans.BeanUtils;

public class Converter {

    public static Object converteModelToDto(Object model, Object dto){
        BeanUtils.copyProperties(model,dto);
        return dto;
    }

    public static Object converteDtoToModel(Object dto, Object model,String propertIgnore){
        BeanUtils.copyProperties(dto,model,propertIgnore);
        return model;
    }
}
