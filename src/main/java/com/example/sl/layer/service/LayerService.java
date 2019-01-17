package com.example.sl.layer.service;

import com.example.sl.layer.model.Layer;
import com.example.sl.layer.model.User;


import java.util.Date;
import java.util.List;

public interface LayerService {
    public List<Layer> findAllLayers();

    public int InsertLayer(Layer layer);

    public int deleteLayer(String layerId);

    public Layer findByLayerName(String layerName);

    public List<Layer> findByDescription(String description);

    public List<Layer> findByTime(Date time1,Date time2);


}
