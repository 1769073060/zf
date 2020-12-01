package com.jiyeyihe.cre.commons.file;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

@Component
public class UnifiedFileUpload {

    @Resource
    private Environment environment;
    @Resource
    private FileUtils fileUtils;

    /**
     * 用户申请
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public String userApply(Long businessId,InputStream content, String fileName) throws Exception {
        String path = FileUploadConsts.APPLY+businessId+"/";
        fileName = System.currentTimeMillis()+"_"+fileName;
        fileUtils.uploadFile(content,path,fileName);
        return path+fileName;
    }

    /**
     * 视频看房
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public String videoView(Long businessId,InputStream content, String fileName) throws Exception {
        String path = FileUploadConsts.VIDEO+businessId+"/";;
        fileName = System.currentTimeMillis()+"_"+fileName;
        fileUtils.uploadFile(content,path,fileName);
        return path+fileName;
    }

    /**
     * 图片看房
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public String picView(Long businessId,InputStream content, String fileName) throws Exception {
        String path = FileUploadConsts.PICTURE+businessId+"/";;
        fileName = System.currentTimeMillis()+"_"+fileName;
        fileUtils.uploadFile(content,path,fileName);
        return path+fileName;
    }

    /**
     * vr看房
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public String vRView(Long businessId,InputStream content, String fileName) throws Exception {
        String path = FileUploadConsts.VR+businessId+"/";;
        fileName = System.currentTimeMillis()+"_"+fileName;
        fileUtils.uploadFile(content,path,fileName);
        return path+fileName;
    }

    /**
     * 语音提醒
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public String voiceReminder(Long businessId,InputStream content, String fileName) throws Exception {
        String path = FileUploadConsts.MUSIC+businessId+"/";;
        fileName = System.currentTimeMillis()+"_"+fileName;
        fileUtils.uploadFile(content,path,fileName);
        return path+fileName;
    }

    public String hypertextFile(Long businessId,InputStream content, String fileName) throws Exception {
        String path = FileUploadConsts.HYPERTEXT+businessId+"/";;
        fileName = System.currentTimeMillis()+"_"+fileName;
        fileUtils.uploadFile(content,path,fileName);
        return environment.getProperty("ftp.address")+path+fileName;
    }


}
