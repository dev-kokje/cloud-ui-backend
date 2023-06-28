package com.cuithree.elementsproviderservice.domain.service;

import com.cuithree.elementsproviderservice.domain.entity.ElementGroup;
import com.cuithree.elementsproviderservice.infrastructure.repository.ElementGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ElementGroupService {

    private final ElementGroupRepository elementGroupRepository;

    public Flux<ElementGroup> getAllServiceTypesInfo() {
        return elementGroupRepository.findAll();
    }
}
