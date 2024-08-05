package ru.otus.order_processing.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.order_processing.dto.UserDto;
import ru.otus.order_processing.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/engineers")
public class EngineerRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getEngineers() {
        List<UserDto> allEngineers = userService.findByRoleId(1);
        return ResponseEntity.status(HttpStatus.OK).body(allEngineers);
    }
}
