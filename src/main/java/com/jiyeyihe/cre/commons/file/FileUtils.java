package com.jiyeyihe.cre.commons.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

@Slf4j
@Component
public class FileUtils {

    @Resource
    private Environment environment;


    /**
     * 上传文件
     *
     * @param inputStream
     * @param filePath
     * @param fileName
     */
    public void uploadFile(InputStream inputStream, String filePath, String fileName) {
        log.info("*****************上传图片到服务器开始*****************" + fileName);
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.connect(environment.getProperty("ftp.server"));
            ftp.login(environment.getProperty("ftp.username"), environment.getProperty("ftp.password"));
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.setControlEncoding("UTF-8");
            String[] str = filePath.split("/");
            for (String string : str) {
                ftp.makeDirectory(string);
                ftp.changeWorkingDirectory(string);
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            boolean result = ftp.storeFile(new String(fileName.getBytes("UTF-8"), "ISO-8859-1"), inputStream);
            inputStream.close();
            log.info("*****************上传图片到服务器结束*****************" + fileName);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            e.printStackTrace();
        }
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 删除文
     *
     * @param
     * @return
     */
    public void deleteFile(String filePath) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FTPClient ftp = null;
                try {
                    ftp = new FTPClient();
                    ftp.connect(environment.getProperty("ftp.server"));
                    ftp.login(environment.getProperty("ftp.username"), environment.getProperty("ftp.password"));
                    System.out.println("*****************开始删除文件*****************" + filePath);

                    String[] newFilePath = filePath.split("/");
                    StringBuffer directory = new StringBuffer();
                    for (int i = 1; i < newFilePath.length - 1; i++) {
                        directory.append("/" + newFilePath[i]);
                    }
                    StringBuffer file = new StringBuffer();
                    for (int i = newFilePath.length - 1; i < newFilePath.length; i++) {
                        file.append("/" + newFilePath[i]);
                    }
                    String fileName = file.substring(1, file.length());
                    ftp.changeWorkingDirectory(directory.toString());
                    ftp.dele(fileName);
                    ftp.logout();
                    System.out.println("*****************成功删除文件*****************" + fileName);
                } catch (Exception e) {
                    System.out.println("删除文件失败");
                    e.printStackTrace();
                } finally {
                    ftp.isConnected();
                }
            }
        }).start();
    }


    /**
     * 单文件夹
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String getIpaURl(String file) throws Exception {
        return  environment.getProperty("ftp.address")+ file;
    }


    /**
     * 多文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String getManyUrl(String file) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        file = file.substring(1, file.length() -1);
        if (file.length() > 1) {
            String[] fileArr = file.split(",");
            for (int i = 0; i < fileArr.length; i++) {
                stringBuffer.append(environment.getProperty("ftp.address"));
                if (i + 1 == fileArr.length) {
                    stringBuffer.append(fileArr[i].replace("\"", ""));
                } else {
                    stringBuffer.append(fileArr[i].replace("\"", "") + ",");
                }
            }
        }
        return stringBuffer.toString();
    }
}
