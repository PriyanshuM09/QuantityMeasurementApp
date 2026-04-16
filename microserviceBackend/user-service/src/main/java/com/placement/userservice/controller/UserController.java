package com.placement.userservice.controller;

import com.placement.userservice.entity.ConversionHistory;
import com.placement.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/history")
    public ConversionHistory save(@RequestBody ConversionHistory history) {
        return service.save(history);
    }

    @GetMapping("/history")
    public List<ConversionHistory> getAll() {
        return service.getAll();
    }
}