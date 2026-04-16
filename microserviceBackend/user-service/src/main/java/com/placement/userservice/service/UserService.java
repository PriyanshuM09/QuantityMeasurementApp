package com.placement.userservice.service;

import com.placement.userservice.entity.ConversionHistory;
import com.placement.userservice.repository.ConversionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ConversionHistoryRepository repository;

    public ConversionHistory save(ConversionHistory history) {
        return repository.save(history);
    }

    public List<ConversionHistory> getAll() {
        return repository.findAll();
    }
}