package ru.otus.order_processing.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.order_processing.dto.TariffDto;
import ru.otus.order_processing.service.TariffService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tariffs")
public class TariffRestController {

    private final TariffService tariffService;

    @GetMapping
    public ResponseEntity<List<TariffDto>> allTariffs() {
        List<TariffDto> actualTariffs = tariffService.findActual();
        if (actualTariffs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(actualTariffs, HttpStatus.OK);
        }
    }
}
