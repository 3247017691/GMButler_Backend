package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.utils.OssTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {
    private final OssTemplate ossTemplate;

    @Autowired
    public UploadController(OssTemplate ossTemplate) {
        this.ossTemplate = ossTemplate;
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException{
        String url = ossTemplate.upload(image.getOriginalFilename(), image.getInputStream());
        return Result.success(url);
    }
}
