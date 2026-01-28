package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.payload.BoxLabelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final QrCodeGenerator qrCodeGenerator;
    private final PdfRenderer pdfRenderer;
    private final SpringTemplateEngine templateEngine;

    public byte[] generateA4BoxLabels(List<Box> boxes) {

        List<BoxLabelDto> labels = boxes.stream()
                .map(this::toDto)
                .toList();

        Context context = new Context();
        context.setVariable("labels", labels);

        String html = templateEngine.process(
                "labels-a4-100x50",
                context
        );

        return pdfRenderer.render(html);
    }

    private BoxLabelDto toDto(Box box) {
        BoxLabelDto dto = new BoxLabelDto();

        dto.setClientCode(box.getBoxGroup().getOrder().getClient().getClientCode());
        dto.setBoxGroupCode(box.getBoxGroup().getBoxGroupCode());
        dto.setDescription(box.getBoxGroup().getDescription());
        dto.setL(String.valueOf(box.getBoxGroup().getLength()));
        dto.setW(String.valueOf(box.getBoxGroup().getWidth()));
        dto.setH(String.valueOf(box.getBoxGroup().getHeight()));
        dto.setWeight(String.valueOf(box.getBoxGroup().getWeight()));

        dto.setQrSvg(
                qrCodeGenerator.generateSvg(box.getId(), 200)
        );

        return dto;
    }

}
