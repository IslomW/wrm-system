package com.sharom.wrm.service;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class MinioService {
    private final MinioClient minioClient;

    public MinioService() {
        this.minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }

    public String uploadQrCode(byte[] qrBytes, String fileName) throws Exception {
        String bucket = "qr-codes";
        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucket).build()
        );

        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucket).build()
            );
        }
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(new ByteArrayInputStream(qrBytes), qrBytes.length, -1)
                        .contentType("image/png")
                        .build()
        );

        return "http://localhost:9000/" + bucket + "/" + fileName;
    }


    public String uploadPhoto(byte[] photoBytes, String fileName) throws Exception {
        String bucket = "box-group-photos";

        // проверяем, существует ли bucket, если нет – создаём
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }

        // загружаем фото
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(new ByteArrayInputStream(photoBytes), photoBytes.length, -1)
                        .contentType("image/jpeg") // можно определить динамически
                        .build()
        );

        // возвращаем публичный URL
        return "http://localhost:9000/" + bucket + "/" + fileName;
    }

    @PostConstruct
    public void init() {
        createBucketIfNotExists("box-group-photos");
        createBucketIfNotExists("qr-codes");

        setPublic("box-group-photos");
        setPublic("qr-codes");
    }

    @PostConstruct
    public void setPublicPolicies() {
        setPublic("box-group-photos");
        setPublic("qr-codes");
    }

    private void setPublic(String bucketName) {
        try {
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config("""
                {
                  "Version":"2012-10-17",
                  "Statement":[
                    {
                      "Effect":"Allow",
                      "Principal":"*",
                      "Action":["s3:GetObject"],
                      "Resource":["arn:aws:s3:::%s/*"]
                    }
                  ]
                }
                """.formatted(bucketName))
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to set policy for " + bucketName, e);
        }
    }

    private void createBucketIfNotExists(String bucketName) {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucketName).build()
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to create bucket: " + bucketName, e);
        }
    }
}
