package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.QrStatus;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.service.MinioService;
import com.sharom.wrm.utils.QrCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class QrService {

    private final BoxRepo boxRepo;

    private final MinioService minioService;

    @Async
    @Transactional
    public void generateAndUploadQr(Box box) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // Генерация QR
            QrCodeGenerator.generateBoxQrPng(box, baos);


            byte[] qrBytes = baos.toByteArray();

            // Загрузка в MinIO
            String qrUrl = minioService.uploadQrCode(qrBytes, box.getId() + ".png");
            box.setQrCode(qrUrl);

            box.setQrStatus(QrStatus.CREATED);

            boxRepo.save(box); // отдельная транзакция сохраняет Box
        } catch (Exception e) {
            // Логируем, можно отправить в очередь ошибок
            e.printStackTrace();
        }
    }
}
