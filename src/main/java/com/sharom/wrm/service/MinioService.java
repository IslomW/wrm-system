package com.sharom.wrm.service;

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
                .credentials("minioadmin", "minioadmin")
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
}
