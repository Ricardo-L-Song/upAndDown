package com.example.sl.layer.service;

import com.example.sl.layer.dao.LayerMapper;
import com.example.sl.layer.model.Layer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("layerService")
@Transactional
public class LayerServiceImpl implements LayerService{

    @Resource
    private LayerMapper layerMapper;
    @Override
    public List<Layer> findAllLayers() {
        return layerMapper.selectAll();
    }

    @Override
    public int InsertLayer(Layer layer) {
        return layerMapper.insert(layer);
    }

    @Override
    public int deleteLayer(String layerId) {
        return layerMapper.deleteByPrimaryKey(layerId);
    }

    @Override
    public Layer findByLayerName(String layerName) {
        return layerMapper.selectByLayerName(layerName);
    }

    @Override
    public List<Layer> findByDescription(String description) {
        return layerMapper.selectByDescription(description);
    }

    @Override
    public List<Layer> findByTime(Date time1, Date time2) {
        return layerMapper.selectByTime(time1,time2);
    }
}
