package com.itheima;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OssTest {
    public static void main(String[] args) throws FileNotFoundException {
        // 区域，查看自己仓库的概览  -> Endpoint（地域节点）外网访问
        String endpoint = "oss-cn-beijing.aliyuncs.com";

        // 密钥对，从环境变量中读取
        String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件，本地文件，完整路径
        InputStream inputStream = new FileInputStream("D:\\.Workspace\\OpenCodeWork\\generated_20260912_103353.png");

        //参数1: 桶名字  参数2: 图片上传后名字   参数3: 图片流
        ossClient.putObject("moning", "generated_20260912_103353.png", inputStream);

        // 关闭OSSClient
        ossClient.shutdown();
        System.out.println("上传完成");
    }
}
