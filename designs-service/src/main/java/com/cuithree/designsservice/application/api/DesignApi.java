package com.cuithree.designsservice.application.api;

import com.cuithree.designsservice.domain.dto.DesignDto;
import com.cuithree.designsservice.domain.model.Design;
import com.cuithree.designsservice.domain.service.DesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/design-details")
public class DesignApi {

    private final DesignService designService;

    @PostMapping
    public Mono<ResponseEntity<Design>> saveDesign(@RequestBody DesignDto request) {
        System.out.println("Providers - " + request.providers());
        return designService.saveNewDesign(request.userId(), request.name(), request.providers())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<List<Design>>> getAllDesignsForUser(@PathVariable String userId) {
        return designService.getDesignsByUserId(userId)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @PutMapping
    public Mono<ResponseEntity<Design>> updateDesign(@RequestBody DesignDto request) {
        return designService.updateExistingItem(request.id(), request.userId(), request.name())
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{userId}/{id}")
    public Mono<ResponseEntity<Void>> deleteDesign(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        return designService.deleteExistingItem(id, userId)
                .map(ResponseEntity::ok);
    }
}
