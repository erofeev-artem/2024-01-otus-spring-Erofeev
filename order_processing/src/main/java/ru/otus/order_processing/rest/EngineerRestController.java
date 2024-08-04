package ru.otus.order_processing.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.order_processing.dto.EngineerDto;
import ru.otus.order_processing.service.EngineerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/engineers")
public class EngineerRestController {

    private final EngineerService engineerService;

    @GetMapping
    public ResponseEntity<List<EngineerDto>> getEngineers() {
        List<EngineerDto> allEngineers = engineerService.findAllEngineers();
        return ResponseEntity.status(HttpStatus.OK).body(allEngineers);
    }
}
