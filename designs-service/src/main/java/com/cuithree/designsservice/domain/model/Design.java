package com.cuithree.designsservice.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record Design (
    String id,
    String userId,
    String name,
    List<String> providers,
    LocalDateTime createdDt,
    LocalDateTime updatedDt
) {}
