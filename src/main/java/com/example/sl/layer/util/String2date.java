package com.example.sl.layer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//String转化为json
public class String2date {
    public Date DateChange(String dateStr){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");
        Date date=new Date();
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            date = sf.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
}
