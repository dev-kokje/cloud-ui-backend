package com.cuithree.designsservice.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record DesignDto (
    String id,
    String userId,
    String name,
    List<String> providers
) {}
