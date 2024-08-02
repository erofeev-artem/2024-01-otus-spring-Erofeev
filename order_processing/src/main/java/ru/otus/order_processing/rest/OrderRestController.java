package ru.otus.order_processing.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

//    @GetMapping
//    public ResponseEntity<List<OrderDto>> getNewOrders() {



//    }

//    @PostMapping
//    public ResponseEntity<List<OrderDto>> setOrderStatus() {

//    }


}
