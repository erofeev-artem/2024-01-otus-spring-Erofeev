package ru.otus.order_processing.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<List<OrderDto>> getNewOrders(@RequestParam("status") OrderStatus status) {
        List<Order> newOrders = orderService.findByOrderStatus(status);
        List<OrderDto> ordersDto = newOrders.stream().map(orderMapper::orderToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ordersDto);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody OrderDto orderDto) {
        orderService.save(orderDto);
        HttpStatus httpStatus;
//        Author author = authorService.findByFullName(book.getAuthorName());
//        List<Genre> genreList = genreService.findByGenreName(book.getGenresNames());
//        Set<Long> genreIdSet = genreList.stream().map(Genre::getId).collect(Collectors.toSet());
//        if (book.getId() != 0) {
//            bookService.update(book.getId(), book.getTitle(), author.getId(), genreIdSet);
//            httpStatus = HttpStatus.OK;
//        } else {
//            bookService.insert(book.getTitle(), author.getId(), genreIdSet);
//            httpStatus = HttpStatus.CREATED;
//        }
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

//    @PostMapping
//    public ResponseEntity<List<OrderDto>> setConnectionConditions() {

//    }


}
