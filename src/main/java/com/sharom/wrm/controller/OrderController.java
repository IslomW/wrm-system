package com.sharom.wrm.controller;

import com.sharom.wrm.entity.Order;
import com.sharom.wrm.entity.OrderStatus;
import com.sharom.wrm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;



    // Создать заказ
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam String clientId) {
        Order order = orderService.createOrder(clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // Получить заказ по ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Order order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    // Получить все заказы
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    // Изменить статус заказа
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable String id,
            @RequestParam OrderStatus newStatus
    ) {
        orderService.changeStatus(id, newStatus);
        return ResponseEntity.noContent().build();
    }

    // Удалить заказ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Получить заказы по clientId
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Order>> getOrdersByClientId(@PathVariable String clientId) {
        List<Order> orders = orderService.getByClientId(clientId);
        return ResponseEntity.ok(orders);
    }

}
