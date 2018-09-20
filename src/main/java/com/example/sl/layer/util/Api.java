package com.example.sl.layer.util;

import com.example.sl.layer.model.Layer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//接口
//code=1 success 2 fail 3 warning
public class Api {
    public Map<String,Object> returnJson(int code, String msg){
        Map map=new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }

    public Map<String,Object> returnJson(int code, String msg, List<Map> data){
        Map map=new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }

    public Map<String,Object> returnJson(int code, String msg, Map data){
        Map map=new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }

    public Map<String,Object> returnJson(int code, String msg, int count,List<Layer> data){
        Map map=new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        map.put("count",count);
        map.put("data",data);
        return map;
    }
}
