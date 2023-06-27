package com.cuithree.designsservice.infrastructure.repository;

import com.cuithree.designsservice.domain.model.Design;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@RequiredArgsConstructor
public class DesignRepositoryImpl implements DesignRepository {

    private final DynamoDbAsyncClient asyncClient;
    private final String tableName;
    private final String indexName;

    @Override
    public Mono<Design> createItem(Design designDetails) {
        var providersList = new ArrayList<AttributeValue>();
        for(String item: designDetails.providers()) {
            providersList.add(AttributeValue.fromS(item));
        }

        var item = new HashMap<String, AttributeValue>();
        item.put("Id", AttributeValue.fromS(designDetails.id()));
        item.put("UserId", AttributeValue.fromS(designDetails.userId()));
        item.put("Name", AttributeValue.fromS(designDetails.name()));
        item.put("Providers", AttributeValue.fromL(providersList));
        item.put("CreatedDt", AttributeValue.fromS(designDetails.createdDt().toString()));
        item.put("UpdatedDt", AttributeValue.fromS(designDetails.updatedDt().toString()));

        var request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        return Mono.fromFuture(asyncClient.putItem(request))
                .thenReturn(designDetails);
    }

    @Override
    public Flux<Design> findByUserId(String userId) {
        var queryAttributes = new HashMap<String, AttributeValue>();
        queryAttributes.put(":userId", AttributeValue.fromS(userId));

        var request = QueryRequest.builder()
                .tableName(tableName)
                .indexName(indexName)
                .keyConditionExpression("UserId = :userId")
                .expressionAttributeValues(queryAttributes)
                .build();

        return Mono.fromFuture(asyncClient.query(request))
                .map(QueryResponse::items)
                .flatMapMany(Flux::fromIterable)
                .map(item -> {
                    var providers = new ArrayList<String>();
                    if(item.containsKey("Providers")) {
                        var providersAttrList = item.get("Providers").l();
                        for(AttributeValue provider: providersAttrList) {
                            providers.add(provider.s());
                        }
                    }
                    return Design.builder()
                            .id(item.getOrDefault("Id", AttributeValue.fromS("0")).s())
                            .userId(item.getOrDefault("UserId", AttributeValue.fromS("0")).s())
                            .name(item.getOrDefault("Name", AttributeValue.fromS("NA")).s())
                            .providers(providers)
                            .createdDt(LocalDateTime.parse(item.getOrDefault("CreatedDt", AttributeValue.fromS(LocalDateTime.now().toString())).s()))
                            .updatedDt(LocalDateTime.parse(item.getOrDefault("UpdatedDt", AttributeValue.fromS(LocalDateTime.now().toString())).s()))
                            .build();
                });
    }

    @Override
    public Mono<Design> updateItem(Design designDetails) {
        var itemKey = new HashMap<String, AttributeValue>();
        itemKey.put("Id", AttributeValue.fromS(designDetails.id()));
        itemKey.put("UserId", AttributeValue.fromS(designDetails.userId()));

        var updateValues = new HashMap<String, AttributeValueUpdate>();
        updateValues.put("Name", AttributeValueUpdate.builder()
                .value(AttributeValue.fromS(designDetails.name()))
                .build());
        updateValues.put("UpdatedDt", AttributeValueUpdate.builder()
                .value(AttributeValue.fromS(designDetails.updatedDt().toString()))
                .build());

        var request = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(itemKey)
                .attributeUpdates(updateValues)
                .build();

        return Mono.fromFuture(asyncClient.updateItem(request))
                .thenReturn(designDetails);
    }

    @Override
    public Mono<Void> deleteItem(String id, String userId) {
        var itemKey = new HashMap<String, AttributeValue>();
        itemKey.put("Id", AttributeValue.fromS(id));
        itemKey.put("UserId", AttributeValue.fromS(userId));

        var request = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(itemKey)
                .build();

        return Mono.fromFuture(asyncClient.deleteItem(request))
                .then();
    }
}
