package com.example.sl.layer.controller;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.sl.layer.model.Layer;
import com.example.sl.layer.service.LayerService;
import com.example.sl.layer.util.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/Index")
public class LayerController {
    public static final Logger logger=LoggerFactory.getLogger(LayerController.class);

    @Autowired
    private LayerService layerService;

    Upload upload=new Upload();

    Api api=new Api();

    String2date string2date=new String2date();

    DeleteFile deleteFile=new DeleteFile();

//    DownloadPdf downloadPdf=new DownloadPdf();

    public static final String RECORD_TIME="2018";

    @RequestMapping(value="Index/layer/layer_list")
    @ResponseBody
    public Map<String,Object> getLayerList(){
        int code;
        List<Layer> layerList=layerService.findAllLayers();
        if (layerList!=null){
            code=0;
        }else {
            code=1;
        }


        Map m2=new HashMap();
        m2.put("code",code);
        m2.put("count",3);
        m2.put("data",layerList);
        m2.put("msg","success");
        return m2;
    }

    /**
     * 处理上传文件方法请求
     * @param file 前台上传的文件对象
     * @return
     */
    @RequestMapping(value = "Index/layer/upload",method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,Object> uploadOne(HttpServletRequest request,@RequestParam("file")MultipartFile file)
    {
       Map map=new HashMap();
        try {
            //上传目录地址
//            String uploadDir = request.getSession().getServletContext().getRealPath("upload");
            String uploadDir = request.getSession().getServletContext().getRealPath("/") +"upload/";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //调用上传方法
           String fileName=upload.executeUpload(uploadDir,file);
            uploadDir=uploadDir.substring(0,uploadDir.length()-1);
            map.put("fileName",fileName);
            map.put("dir",uploadDir);
        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            return api.returnJson(2,"上传失败",map);
        }

        return api.returnJson(1,"上传成功",map);
    }

    /**
     * 处理Excel解析的方法
     * @param file 前台上传的文件对象
     * @return
     */
    @RequestMapping(value = "Index/layer/excelparser")
    @ResponseBody
    public   Map<String,Object> Excel(HttpServletRequest request,@RequestParam("file")MultipartFile file)throws Exception
    {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String fileName1 = request.getParameter("fileName");// 设置文件名，根据业务需要替换成要下载的文件名
        String fileName;
        try {
            //上传目录地址


            String uploadDir = request.getSession().getServletContext().getRealPath("/") +"upload/";

            uploadDir=uploadDir.substring(0,uploadDir.length()-1);
            uploadDir=uploadDir+"\\";//下载目录
            String realPath=uploadDir+fileName1;//
            File dir = new File(realPath);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //调用上传方法
            fileName=upload.executeUpload1(uploadDir, file,fileName1);
            uploadDir=uploadDir.substring(0,uploadDir.length()-1);
            dataMap.put("fileName",fileName);
            dataMap.put("dir",uploadDir);
        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            return api.returnJson(2,"解析失败",dataMap);
        }
        ExcelParser(fileName);
        return api.returnJson(1,"解析成功",dataMap);
    }

    public void ExcelParser(String fileName)throws Exception{
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        long start = new Date().getTime();
        List<Layer> list=new ArrayList<>();
        list = upload.importExcel("C:/Users/sl/Desktop/layer/layer/src/main/webapp/upload/"+fileName, 1, 1, Layer.class);
        System.out.println(new Date().getTime() - start);
        System.out.println(list.size());
        System.out.println(list);
        int testId=1;
        int isInsert=0;
        for (int i = 0; i <list.size() ; i++) {
            Layer layer=new Layer();
            UUID uuid=UUID.randomUUID();
            String layerId=uuid.toString();
            layer.setLayerId(layerId);
            layer.setLayerName(list.get(i).getLayerName());
            layer.setDescription(list.get(i).getDescription());
            layer.setRecordTime(list.get(i).getRecordTime());
            layer.setReleaseTime(list.get(i).getReleaseTime());
            int is_add=layerService.InsertLayer(layer);
            System.out.println(is_add);
        }

        //这里是设置Actionlist的testid，方便之后的插入
//        for(int i=0;i<list.size();i++){
//            if(list.get(i).getTestDescription()!=null&&list.get(i).getTestDescription()!=""){//如果不空，testId更新 并且将这条数据插入Test表
//                PageData vpd = new PageData();
//                vpd.put("TEST_ID", list.get(i).getTestId());
//                vpd.put("TEST_NAME", list.get(i).getTestName());
//                vpd.put("TEST_DESCRIPTION", list.get(i).getTestDescription());
//                vpd.put("TEST_INIT", list.get(i).getTestInit());
//                vpd.put("TEST_PREMISE", list.get(i).getTestPremise());
//                vpd.put("TEST_METHOD", list.get(i).getTestMethod());
//                vpd.put("TEST_ABORT", list.get(i).getTestAbort());
//                vpd.put("TEST_ASSESS", list.get(i).getTestAssess());
//                vpd.put("TEST_DESIGN", list.get(i).getTestDesign());
//                testService.saveD(vpd);
//
//                PageData apd = new PageData();
//                String action_id = UuidUtil.get32UUID();
//                apd.put("ACTION_ID",action_id);
//                apd.put("OPERATION",list.get(i).getActionList().get(0).getOperation());
//                apd.put("FORWARD",list.get(i).getActionList().get(0).getForward());
//                apd.put("ASSESS",list.get(i).getActionList().get(0).getAssess());
//                apd.put("TEST_ID",list.get(i).getTestId());
//
//                actionService.saveA(apd);
//                list.get(i).getActionList().get(0).setTestId(list.get(i).getTestId());
//                testId=list.get(i).getTestId();
//                /*isInsert=testService.InsertTest(list.get(i));*/
//                list.get(i).getActionList().get(0).setTestId(testId);
//                testId+=1;
//                continue;
//            }//如果为空，testId不变 将数据插入Action表
//            if(list.get(i).getActionList().get(0).getAssess()==null){
//                continue;
//            }
//            list.get(i).getActionList().get(0).setTestId(testId-1);
//            PageData apd = new PageData();
//            String action_id = UuidUtil.get32UUID();
//            apd.put("ACTION_ID",action_id);
//            apd.put("OPERATION",list.get(i).getActionList().get(0).getOperation());
//            apd.put("FORWARD",list.get(i).getActionList().get(0).getForward());
//            apd.put("ASSESS",list.get(i).getActionList().get(0).getAssess());
//            apd.put("TEST_ID",list.get(i).getActionList().get(0).getTestId());
//            actionService.saveA(apd);
//
//        }
       System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
       System.out.println(ReflectionToStringBuilder.toString(list.get(1)));
//        System.out.println(ReflectionToStringBuilder.toString(list.get(3)));
//        System.out.println(ReflectionToStringBuilder.toString(list.get(4)));
//        System.out.println(ReflectionToStringBuilder.toString(list.get(0).getActionList().get(0)));
//        System.out.println(ReflectionToStringBuilder.toString(list.get(1).getActionList().get(0)));
//        System.out.println(ReflectionToStringBuilder.toString(list.get(3).getActionList().get(0)));
//        System.out.println(ReflectionToStringBuilder.toString(list.get(4).getActionList().get(0)));
        //到这一步 已经将Test数据放入了list,现在要进行操作
    }

    //下载
    @RequestMapping(value = "Index/layer/download")
    @ResponseBody
    public Map<String,Object> downloadOne(HttpServletRequest req,HttpServletResponse response) throws IOException{
        String fileName = req.getParameter("fileName");// 设置文件名，根据业务需要替换成要下载的文件名
//        String layerId = req.getParameter("layerId");
        String downloadDir = req.getSession().getServletContext().getRealPath("/") +"upload/";
//        String savePath = req.getSession().getServletContext().getRealPath("/") +"download/";
        downloadDir=downloadDir.substring(0,downloadDir.length()-1);
        downloadDir=downloadDir+"\\";//下载目录
        String realPath=downloadDir+fileName;//
        File file = new File(realPath);//下载目录加文件名拼接成realpath
        if(file.exists()){ //判断文件父目录是否存在
//            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + fileName);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return api.returnJson(2,"fail"+realPath+fileName);
    }

    //增
    @RequestMapping(value="Index/layer/add_layer")
    @ResponseBody
    public Map<String,Object> addLayer(HttpServletRequest req) throws IOException{
        String fileName = req.getParameter("fileName");
        String layerName=req.getParameter("layerName");
        String description=req.getParameter("description");//有注解，默认转换
        String strReleaseTime=req.getParameter("releaseTime");
        if (layerName==null){
            return api.returnJson(3,"warning");
        }
        UUID uuid=UUID.randomUUID();
        String layerId=uuid.toString();
        Layer layer=new Layer();
        Date releaseTime=string2date.DateChange(strReleaseTime);
        Date recordTime=string2date.DateChange(RECORD_TIME);
        layer.setLayerId(layerId);
        layer.setLayerName(layerName);
        layer.setFileName(fileName);
        layer.setReleaseTime(releaseTime);
        layer.setDescription(description);
        layer.setRecordTime(recordTime);
//        Map map=new HashMap();
//        map.put("11",layer);
//        return api.returnJson(3,"warning",map);

        int is_add=layerService.InsertLayer(layer);
        if (is_add!=0){
            return api.returnJson(1,"添加成功");
        }else{
            return api.returnJson(2,"添加失败");
        }
    }

    //删
    @RequestMapping(value = "Index/layer/del_layer",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delLayer(HttpServletRequest req) throws IOException{
        String fileName = req.getParameter("fileName");
        String layerId = req.getParameter("layerId");
        String uploadDir = req.getSession().getServletContext().getRealPath("/") +"upload/";
        uploadDir=uploadDir.substring(0,uploadDir.length()-1);

        String path=uploadDir+"\\"+fileName;
        boolean is_del_file=deleteFile.excuteDelete(path);
//        Map map=new HashMap();
//        map.put("1",is_del_file);
//        return api.returnJson(2,"fail",map);
        int is_del=layerService.deleteLayer(layerId);//删除模板表
        if (is_del!=0&&is_del_file==true){
            return api.returnJson(1,"success");
        }else {
            return api.returnJson(2,"fail");
        }
    }


//    查
    @RequestMapping(value="Index/layer/sel")
    @ResponseBody
    public Map<String,Object> selLayer(HttpServletRequest req) throws IOException{
        String layerName = req.getParameter("layerName");
        String description = req.getParameter("description");
        String strReleaseTime1 = req.getParameter("releaseTime1");
        String strReleaseTime2 = req.getParameter("releaseTime2");
        List<Layer> layerList=new ArrayList<>();
        if (layerName==""){
             return api.returnJson(0,"请填写法律法规名");
        }else if(description==""){//有layerName 那就查询这个法律法规名
            if (layerService.findByLayerName(layerName)!=null) {
                layerList.add(layerService.findByLayerName(layerName));
                return api.returnJson(0, "success", 2, layerList);
            }else return api.returnJson(0,"暂无数据");
        }else if(strReleaseTime1==""){//有layerName和descruption
            layerList=layerService.findByDescription(description);
            if (layerList!=null&&layerList.size()>0)
            return api.returnJson(0,"success",2,layerList);
            else return api.returnJson(0,"暂无数据");
        }else{//全都有
            Date releaseTime1=string2date.DateChange(strReleaseTime1);
            Date releaseTime2=string2date.DateChange(strReleaseTime2);
            layerList=layerService.findByTime(releaseTime1,releaseTime2);
            if (layerList!=null&&layerList.size()>0)
                return api.returnJson(0,"success",2,layerList);
            else return api.returnJson(0,"暂无数据");
//            return api.returnJson(0,"success",2,layerList);
        }
    }

}
