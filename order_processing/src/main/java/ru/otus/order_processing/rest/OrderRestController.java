package ru.otus.order_processing.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.order_processing.dto.EngineerOrderUpdateDto;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.mapper.OrderMapper;
import ru.otus.order_processing.model.Order;
import ru.otus.order_processing.model.OrderStatus;
import ru.otus.order_processing.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @GetMapping("/new")
    public ResponseEntity<List<OrderDto>> getNewOrders() {
        List<Order> newOrders = orderService.findByOrderStatus(OrderStatus.NEW);
        List<OrderDto> ordersDto = newOrders.stream().map(orderMapper::orderToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ordersDto);
    }

    @GetMapping("/confirmed")
    public ResponseEntity<List<OrderDto>> getConfirmedOrders() {
        List<Order> newOrders = orderService.findByOrderStatus(OrderStatus.CONFIRMED);
        List<OrderDto> ordersDto = newOrders.stream().map(orderMapper::orderToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ordersDto);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<OrderDto>> getCompletedOrders() {
        List<Order> newOrders = orderService.findByOrderStatus(OrderStatus.COMPLETED);
        List<OrderDto> ordersDto = newOrders.stream().map(orderMapper::orderToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ordersDto);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<OrderDto>> getRejectedOrders() {
        List<Order> newOrders = orderService.findByOrderStatus(OrderStatus.REJECTED);
        List<OrderDto> ordersDto = newOrders.stream().map(orderMapper::orderToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ordersDto);
    }

    @GetMapping("/assigned")
    public ResponseEntity<List<OrderDto>> getAssignedOrders(@RequestParam long engineerId) {
        List<OrderDto> orders = orderService.findAssignedOrders(engineerId).stream()
                .map(orderMapper::orderToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody OrderDto orderDto) {
        orderService.saveOrder(orderDto);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/update")
    public ResponseEntity<OrderDto> update(@RequestBody EngineerOrderUpdateDto updateDto) {
        OrderDto orderDto = orderService.update(updateDto);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}
