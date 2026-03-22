package com.sharom.wrm.modules.order.controller;

import com.sharom.wrm.common.response.ApiResponse;
import com.sharom.wrm.common.response.ResponseFactory;
import com.sharom.wrm.modules.order.model.entity.Order;
import com.sharom.wrm.modules.order.model.entity.OrderStatus;
import com.sharom.wrm.modules.order.service.OrderService;
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

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestParam String clientId) {
        Order order = orderService.createOrder(clientId);
        return ResponseFactory.created(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable String id) {
        Order order = orderService.getById(id);
        return ResponseFactory.ok(order);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAll();
        return ResponseFactory.ok(orders);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable String id,
            @RequestParam OrderStatus newStatus
    ) {
        orderService.changeStatus(id, newStatus);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByClientId(@PathVariable String clientId) {
        List<Order> orders = orderService.getByClientId(clientId);
        return ResponseFactory.ok(orders);
    }

}
