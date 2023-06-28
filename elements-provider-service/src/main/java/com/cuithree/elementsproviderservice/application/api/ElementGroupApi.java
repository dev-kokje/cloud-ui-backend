package com.cuithree.elementsproviderservice.application.api;

import com.cuithree.elementsproviderservice.domain.entity.ElementGroup;
import com.cuithree.elementsproviderservice.domain.service.ElementGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/element_group")
@RequiredArgsConstructor
public class ElementGroupApi {

    private final ElementGroupService elementGroupService;

    @GetMapping
    public Flux<ElementGroup> getAllElementGroups() {
        return  elementGroupService.getAllServiceTypesInfo();
    }
}
