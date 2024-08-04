package ru.otus.order.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.order.dto.OrderDto;
import ru.otus.order.kafka.KafkaProducer;
import ru.otus.order.service.AllowedAddressService;
import ru.otus.order.service.ConnectionAddressService;
import ru.otus.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    private final AllowedAddressService allowedAddressService;

    private final ConnectionAddressService connectionAddressService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody OrderDto orderDto) {
        if (!allowedAddressService.checkHouseAddress(orderDto.connectionAddressDto())) {
            return new ResponseEntity<>("Ваш дом не подключен", HttpStatus.OK);
        } else if (connectionAddressService.checkApartmentAddress(orderDto.connectionAddressDto())) {
            return new ResponseEntity<>("Этот адрес уже подключен", HttpStatus.OK);
        } else {
            orderService.send(orderDto);
            return new ResponseEntity<>("Спасибо за заявку, наш оператор свяжется с вами в ближайшее время",
                    HttpStatus.CREATED);
        }
    }
}