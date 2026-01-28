package com.sharom.wrm.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

@Component
public class QrCodeGenerator {
    public String generateSvg(String text, int size) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(
                    text,
                    BarcodeFormat.QR_CODE,
                    size,
                    size
            );

            StringBuilder svg = new StringBuilder();
            svg.append("<svg xmlns='http://www.w3.org/2000/svg' ")
                    .append("viewBox='0 0 ").append(size).append(" ").append(size).append("' ")
                    .append("shape-rendering='crispEdges'>");

            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (matrix.get(x, y)) {
                        svg.append("<rect x='").append(x)
                                .append("' y='").append(y)
                                .append("' width='1' height='1' fill='black'/>");
                    }
                }
            }

            svg.append("</svg>");
            return svg.toString();

        } catch (Exception e) {
            throw new RuntimeException("QR SVG generation failed", e);
        }
    }
}
