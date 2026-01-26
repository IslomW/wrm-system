package com.sharom.wrm.utils;

import com.google.zxing.BarcodeFormat;
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
import java.io.OutputStream;

public class QrCodeGenerator {

    public static void generateBoxQrPng(Box box, OutputStream os) throws WriterException, IOException {
        int qrSize = 150; // размер QR
        int width = 400;  // ширина финальной картинки
        int height = 200; // высота финальной картинки

        // Создаем QR-код
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(box.getId(), BarcodeFormat.QR_CODE, qrSize, qrSize);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Создаем финальное изображение
        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = combined.createGraphics();

        // фон
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // рисуем QR
        g.drawImage(qrImage, width - qrSize - 10, 10, null);

        // текст слева
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        int topMargin = 40;
        int y = topMargin;
        g.drawString(box.getBoxGroup().getOrder().getClient().getClientCode(), 10, y); y += 25;
        g.drawString(box.getBoxGroup().getBoxGroupCode(), 10, y); y += 25;
        g.drawString(box.getBoxGroup().getDescription(), 10, y); y += 25;
        g.drawString("LWH: " + box.getBoxGroup().getLength() + "/" + box.getBoxGroup().getWidth() + "/" + box.getBoxGroup().getHeight() +
                " W: " + box.getBoxGroup().getWeight(), 10, y);

        g.dispose();

        // сохраняем PNG
        ImageIO.write(combined, "PNG", os);
    }
}
