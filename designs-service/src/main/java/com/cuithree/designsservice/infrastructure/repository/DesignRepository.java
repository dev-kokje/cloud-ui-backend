package com.cuithree.designsservice.infrastructure.repository;

import com.cuithree.designsservice.domain.model.Design;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DesignRepository {

    public Mono<Design> createItem(Design designDetails);

    public Flux<Design> findByUserId(String userId);

    public Mono<Design> updateItem(Design designDetails);

    public Mono<Void> deleteItem(String id, String userId);
}
