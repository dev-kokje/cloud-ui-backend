package com.cuithree.designsservice.infrastructure.config;

import com.cuithree.designsservice.infrastructure.repository.DesignRepository;
import com.cuithree.designsservice.infrastructure.repository.DesignRepositoryImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

@Configuration
public class DynamoDBConfiguration {

    @Value("${aws.dynamodb.endpoint}")
    private String dynamoDBEndpoint;
    @Value("${aws.dynamodb.accessKey}")
    private String accessKey;
    @Value("${aws.dynamodb.secretKey}")
    private String secretKey;
    @Value("${aws.dynamodb.tableName}")
    private String tableName;
    @Value("${aws.dynamodb.indexName}")
    private String indexName;

    @SneakyThrows
    private DynamoDbAsyncClient amazonDynamoDBAsync() {
        var credentials = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
        return DynamoDbAsyncClient.builder()
                .endpointOverride(new URI(dynamoDBEndpoint))
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(credentials)
                .build();
    }

    @Bean
    public DesignRepository designDetailsRepository() {
        return new DesignRepositoryImpl(
                amazonDynamoDBAsync(),
                tableName,
                indexName
        );
    }
}
