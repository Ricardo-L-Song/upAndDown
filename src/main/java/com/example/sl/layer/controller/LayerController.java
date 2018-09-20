package com.example.sl.layer.controller;

import com.example.sl.layer.model.Layer;
import com.example.sl.layer.service.LayerService;
import com.example.sl.layer.util.*;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
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
