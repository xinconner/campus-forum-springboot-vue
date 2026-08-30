package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.StoreImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

public interface ImageService extends IService<StoreImage> {
    String uploadAvatar(MultipartFile file,int id) throws IOException;
    String uploadImage(MultipartFile file,int id) throws IOException;
    void fetchImageFromMinio(OutputStream stream,String image) throws Exception;
}
