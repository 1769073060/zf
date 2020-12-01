package com.jiyeyihe.cre.webapp.web.pub;

import com.jiyeyihe.cre.commons.file.UnifiedFileUpload;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "通用文件上传")
@RestController
@RequestMapping("pub/upload")
public class UploadFileController {

    @Resource
    private UnifiedFileUpload unifiedFileUpload;


    /**
     * 0商家申请图片，1视频看房，2图片看房，3VR看房，4语音提醒,5超文本提交
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("fileUpload")
    @ApiOperation(httpMethod = "POST", value = "统一文件上传")
    public Result uploadFile(HttpServletRequest request) throws Exception {
        Result result = null;
        Integer pathType = Integer.parseInt(request.getParameter("pathType"));
        Long businessId = 0L;
        if(pathType==0){
            businessId = Long.parseLong(request.getParameter("businessId"));
        }
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile multipartFile =  req.getFile("file");
        Object[] objects = {pathType,multipartFile};
        if(isEmpty(objects)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        byte [] file=multipartFile.getBytes();
        InputStream content = new ByteArrayInputStream(file);
        String path = null;
        switch (pathType){
            case 0:
                path=unifiedFileUpload.userApply(businessId,content,multipartFile.getOriginalFilename());
                break;
            case 1:
                path=unifiedFileUpload.videoView(businessId,content,multipartFile.getOriginalFilename());
                break;
            case 2:
                path=unifiedFileUpload.picView(businessId,content,multipartFile.getOriginalFilename());
                break;
            case 3:
                path=unifiedFileUpload.vRView(businessId,content,multipartFile.getOriginalFilename());
                break;
            case 4:
                path=unifiedFileUpload.voiceReminder(businessId,content,multipartFile.getOriginalFilename());
                break;
            case 5:
                path=unifiedFileUpload.hypertextFile(businessId,content,multipartFile.getOriginalFilename());
                break;
        }
        try {
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,path);
        }catch (Exception e){
            log.info(e.getMessage(),e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
