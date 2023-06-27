package com.cuithree.designsservice.domain.service;

import com.cuithree.designsservice.domain.model.Design;
import com.cuithree.designsservice.infrastructure.repository.DesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DesignService {

    private final DesignRepository designRepository;

    public Mono<Design> saveNewDesign(
            String userId,
            String name,
            List<String> providers
    ) {
        return designRepository.createItem(Design.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .name(name)
                .providers(providers)
                .createdDt(LocalDateTime.now())
                .updatedDt(LocalDateTime.now())
                .build());
    }

    public Flux<Design> getDesignsByUserId(String userId) {
        return designRepository.findByUserId(userId);
    }

    public Mono<Design> updateExistingItem(String id, String userId, String name) {
        return designRepository.updateItem(Design.builder()
                .id(id)
                .userId(userId)
                .name(name)
                .updatedDt(LocalDateTime.now())
                .build());
    }

    public Mono<Void> deleteExistingItem(String id, String userId) {
        return designRepository.deleteItem(id, userId);
    }

}
