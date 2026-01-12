package com.sharom.wrm.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class MinioService {
    private final MinioClient minioClient;

    public MinioService() {
        this.minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("admin", "admin12345")
                .build();
    }

    public String uploadQrCode(byte[] qrBytes, String fileName) throws Exception {
        String bucket = "qr-codes";

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
}
