package com.sharom.wrm.service.impl;

import com.sharom.wrm.config.CustomUserDetails;
import com.sharom.wrm.entity.*;
import com.sharom.wrm.mapper.BoxGroupMapper;
import com.sharom.wrm.mapper.BoxMapper;
import com.sharom.wrm.payload.BoxLabelDto;
import com.sharom.wrm.payload.box.BoxDTO;
import com.sharom.wrm.payload.box.BoxGroupDTO;
import com.sharom.wrm.payload.box.BoxGroupResponseDTO;
import com.sharom.wrm.repo.BoxGroupRepo;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.repo.OrderRepo;
import com.sharom.wrm.repo.WarehouseRepo;
import com.sharom.wrm.service.BoxGroupService;
import com.sharom.wrm.service.MinioService;
import com.sharom.wrm.service.QrCodeService;
import com.sharom.wrm.utils.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sharom.wrm.entity.BoxStatus.CREATED;

@Service
@RequiredArgsConstructor
public class BoxGroupServiceImpl implements BoxGroupService {

    private final BoxGroupRepo boxGroupRepo;
    private final BoxRepo boxRepo;
    private final OrderRepo orderRepo;
    private final WarehouseRepo warehouseRepo;
    private final QrService qrService;
    private final MinioService minioService;
    private final BoxGroupMapper boxGroupMapper;
    private final BoxMapper boxMapper;


    private final QrCodeGenerator qrCodeGenerator;
    private final PdfRenderer pdfRenderer;
    private final SpringTemplateEngine templateEngine;

    @Override
    @Transactional
    public BoxGroupDTO createGroup(String orderId, BoxGroupDTO dto, List<MultipartFile> photos) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        CustomUserDetails userDetails = SecurityUtils.currentUser();

        Warehouse warehouse = warehouseRepo.findById(userDetails.getLocationId())
                .orElseThrow(()-> new RuntimeException("Warehouse not found"));

//        if (dto.quantity() <= 0) {
//            throw new IllegalArgumentException("Quantity must be greater than 0");
//        }
//
//        Warehouse warehouse = warehouseRepo.findById(dto.warehouseId())
//                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        BoxGroup boxGroup = new BoxGroup();
        boxGroup.setOrder(order);
        boxGroup.setWarehouse(warehouse);
        boxGroup.setDescription(dto.description());
        boxGroup.setWeight(dto.weight());
        boxGroup.setLength(dto.length());
        boxGroup.setWidth(dto.width());
        boxGroup.setHeight(dto.height());
        boxGroup.setBoxType(dto.boxType());
        boxGroup.setQuantity(dto.quantity());
        boxGroup.setPhotoUrls(uploadPhotos(photos));

        String groupCode = BoxNumberGenerator.generateGroupCode(boxGroupRepo.countByOrderId(orderId) + 1);
        boxGroup.setBoxGroupCode(groupCode);

        BoxGroup savedGroup = boxGroupRepo.save(boxGroup);

        List<Box> boxes = new ArrayList<>();
        for (int i = 1; i <= dto.quantity(); i++) {
            Box box = new Box();
            box.setId(TsidGenerator.next());
            box.setBoxGroup(savedGroup);
            box.setCurrentWarehouse(warehouse);
            box.setStatus(CREATED);
            box.setBoxNumber(BoxNumberGenerator.generateBoxNumber(groupCode, i));

            boxes.add(box);
        }

        savedGroup.setBoxes(boxes);

        return boxGroupMapper.boxGroupToDTO(boxGroup);
    }


    @Override
    public byte[] getQrcode(String boxGroupId) {
        BoxGroup boxGroup = boxGroupRepo.findById(boxGroupId)
                .orElseThrow(() -> new RuntimeException("BoxGroup not found"));

        List<Box> boxes = boxGroup.getBoxes();

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

    @Override
    @Transactional
    public void addBoxToGroup(String groupId, String boxId) {
        BoxGroup group = boxGroupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("BoxGroup not found"));

        Box box = boxRepo.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        // Если коробка уже в другой группе
        if (box.getBoxGroup() != null && !box.getBoxGroup().getId().equals(groupId)) {
            throw new RuntimeException("Box already belongs to another group");
        }

        // Связываем
        box.setBoxGroup(group);
        boxRepo.save(box);

        // Обновляем список коробок в группе (опционально, если есть mappedBy)
        group.getBoxes().add(box);
        boxGroupRepo.save(group);
    }

    @Override
    @Transactional
    public void removeBoxFromGroup(String groupId, String boxId) {

        BoxGroup group = boxGroupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("BoxGroup not found"));

        Box box = boxRepo.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        if (!group.equals(box.getBoxGroup())) {
            throw new RuntimeException("Box does not belong to this group");
        }

       boxRepo.delete(box);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoxDTO> getBoxes(String groupId) {
        BoxGroup group = boxGroupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("BoxGroup not found"));

        // Возвращаем копию списка, чтобы фронт не изменял оригинал
        return boxMapper.toDtoList(group.getBoxes());
    }

    @Override
    public PageDTO<BoxGroupDTO> getGroupBox(Pageable pageable) {

        CustomUserDetails userDetails = SecurityUtils.currentUser();
        if (userDetails.getLocationId().isEmpty()){
            throw new RuntimeException("User not logged in");
        }

        return Page2DTO.tPageDTO(boxGroupRepo.findByWarehouseId(userDetails.getLocationId(), pageable).map(boxGroupMapper::boxGroupToDTO));
    }

    private List<String> uploadPhotos(List<MultipartFile> files){
        List<String> urls = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile photo : files) {

                if (photo.isEmpty()) continue;

                if (!photo.getContentType().startsWith("image/")) {
                    throw new RuntimeException("Only images allowed");
                }

                try {
                    String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();

                    String photoUrl = minioService.uploadPhoto(
                            photo.getBytes(),
                            fileName
                    );

                    urls.add(photoUrl);

                } catch (Exception e) {
                    throw new RuntimeException("Error uploading photo", e);
                }
            }
        }

        return urls;
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
