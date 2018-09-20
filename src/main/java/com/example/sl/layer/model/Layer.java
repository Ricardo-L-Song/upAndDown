package com.example.sl.layer.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Layer {
    private String layerId;

    private String layerName;

    private String description;

    @JsonFormat(pattern="yyyy",timezone="GMT+8")
    private Date releaseTime;

    @JsonFormat(pattern="yyyy",timezone="GMT+8")
    private Date recordTime;

    private String fileName;

    public String getLayerId() {
        return layerId;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId == null ? null : layerId.trim();
    }

    public String getLayerName() {
        return layerName;
    }


    public void setLayerName(String layerName) {
        this.layerName = layerName == null ? null : layerName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    @JsonFormat(pattern="yyyy",timezone="GMT+8")
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    @JsonFormat(pattern="yyyy",timezone="GMT+8")
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
}