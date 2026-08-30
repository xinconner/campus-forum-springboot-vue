package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.ImageService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Resource
    ImageService service;

    @PostMapping("/cache")
    public RestBean<String> uploadImage(@RequestParam("file")MultipartFile file,
                                        @RequestAttribute(Const.ATTR_USER_ID) int id,
                                        HttpServletResponse response) throws IOException {
        if (file.getSize()>1024*1024*6){
            return RestBean.failure(400,"头像图片大小不能大于6MB");
        }
        log.info("正在进行图片上传操作...");
        String url = service.uploadImage(file,id);
        if (url!=null){
            log.info("图片上传成功，大小："+file.getSize());
            return RestBean.success(url);
        }else {
            response.setStatus(400);
            return RestBean.failure(400,"图片上传失败，请联系管理员！");
        }
    }

    @PostMapping("/avatar")
    public RestBean<String> uploadAvatar(@RequestParam("file")MultipartFile file,
                                         @RequestAttribute(Const.ATTR_USER_ID) int id,
                                         HttpServletResponse response) throws IOException {
        if (file.getSize()>1024*1000)
            return RestBean.failure(400,"头像图片大小不能大于1MB");
        log.info("正在进行头像上传操作...");
        String url = service.uploadAvatar(file,id);
        if (url!=null){
            log.info("头像上传成功，大小："+file.getSize());
            return RestBean.success(url);
        }else {
            return RestBean.failure(400,"头像上传失败，请联系管理员！");
        }
    }

}
