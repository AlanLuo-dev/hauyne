package com.luoyx.hauyne.file.service;

import com.luoyx.hauyne.file.config.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final MinioProperties properties;

    /**
     * 初始化 bucket
     */
    @PostConstruct
    public void initBucket() throws Exception {
        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(properties.getBucket())
                        .build()
        );

        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(properties.getBucket())
                            .build()
            );
        }
    }

    /**
     * 上传文件
     */
    public String upload(MultipartFile file) throws Exception {

        String objectKey = UUID.randomUUID() + "_" + file.getOriginalFilename();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(objectKey)
                        .stream(file.getInputStream(), file.getSize(), -1L)
                        .contentType(file.getContentType())
                        .build()
        );

        return objectKey;
    }

    /**
     * 获取下载流
     */
    public InputStream download(String objectKey) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(objectKey)
                        .build()
        );
    }

    /**
     * 生成预签名 URL（推荐前端直链）
     */
    public String presignedUrl(String objectKey) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Http.Method.GET)
                        .bucket(properties.getBucket())
                        .object(objectKey)
                        .expiry(60 * 30) // 30分钟
                        .build()
        );
    }
}
