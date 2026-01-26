package com.sharom.wrm.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sharom.wrm.entity.Box;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QrCodeGenerator {

    public static void generateBoxQrPng(Box box, String filePath) throws WriterException, IOException {
        int qrSize = 190; // размер QR
        int width = 400;  // ширина финальной картинки
        int height = 200; // высота финальной картинки

        // Создаем QR-код
        QRCodeWriter qrWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0); // 0 = без белой рамки (можно 1, если сканер капризный)

        BitMatrix bitMatrix = qrWriter.encode(
                box.getId(),
                BarcodeFormat.QR_CODE,
                qrSize,
                qrSize,
                hints
        );

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Создаем финальное изображение
        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = combined.createGraphics();

        // фон
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // рисуем QR
        g.drawImage(qrImage, width - qrSize - 5, 5, null);

        Font font18 = new Font("Arial", Font.BOLD, 18);
        Font font24 = new Font("Arial", Font.BOLD, 24);
        Font font32 = new Font("Arial", Font.BOLD, 32);


        // текст слева
        g.setColor(Color.BLACK);
        g.setFont(font32);
        int topMargin = 40;
        int y = topMargin;
        g.drawString(box.getBoxGroup().getOrder().getClient().getClientCode(), 10, y); y += 35;
        g.setFont(font24);
        g.drawString(box.getBoxNumber() +" | "+ box.getBoxGroup().getQuantity(), 10, y); y += 65;
        g.setFont(font18);
        g.drawString(box.getBoxGroup().getDescription(), 10, y); y += 25;
        g.drawString("LWH: " + box.getBoxGroup().getLength() + "/" + box.getBoxGroup().getWidth() + "/" + box.getBoxGroup().getHeight(), 10, y); y += 25;
        g.drawString("W: " + box.getBoxGroup().getWeight(), 10, y);


        g.dispose();

        // сохраняем PNG
        ImageIO.write(combined, "PNG", new File(filePath));
    }
}
