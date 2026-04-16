package com.placement.userservice.repository;

import com.placement.userservice.entity.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {
}