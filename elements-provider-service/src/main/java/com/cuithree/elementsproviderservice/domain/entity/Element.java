package com.cuithree.elementsproviderservice.domain.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public record Element(
        @Field("id")
        Integer id,
        String icon,
        String shortName,
        String fullName
) {}
