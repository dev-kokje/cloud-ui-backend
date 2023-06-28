package com.cuithree.elementsproviderservice.infrastructure.repository;

import com.cuithree.elementsproviderservice.domain.entity.ElementGroup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementGroupRepository extends ReactiveCrudRepository<ElementGroup, String> {

}
