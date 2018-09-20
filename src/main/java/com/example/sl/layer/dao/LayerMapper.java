package com.example.sl.layer.dao;

import com.example.sl.layer.model.Layer;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LayerMapper {
    int deleteByPrimaryKey(String layerId);

    int insert(Layer record);

    Layer selectByPrimaryKey(String layerId);

    List<Layer> selectAll();

    Layer selectByLayerName(String layerName);

    List<Layer> selectByDescription(String description);

    int updateByPrimaryKey(Layer record);

    List<Layer> selectByTime(@Param("time1")Date releaseTime1,@Param("time2") Date releaseTime2);
}