package com.cuithree.elementsproviderservice.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("element_group")
public record ElementGroup(
        @Id
        String id,
        String name,
        List<Category> categories
) {}