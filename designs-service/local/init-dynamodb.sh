#!/bin/bash

ENDPOINT_URL="http://localhost:8000"

# -- > Create DynamoDb Table
echo Creating  DynamoDb \'DesignDetails\' table ...
aws dynamodb --endpoint-url="$ENDPOINT_URL" create-table \
--table-name DesignDetails \
--attribute-definitions AttributeName=Id,AttributeType=S \
                        AttributeName=UserId,AttributeType=S \
--key-schema AttributeName=Id,KeyType=HASH \
             AttributeName=UserId,KeyType=RANGE \
--provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=5 \
--global-secondary-indexes \
    "[
        {
            \"IndexName\": \"UserIdIndex\",
            \"KeySchema\": [{\"AttributeName\":\"UserId\",\"KeyType\":\"HASH\"}],
            \"Projection\":{
                \"ProjectionType\":\"ALL\"
            },
            \"ProvisionedThroughput\": {
                \"ReadCapacityUnits\": 10,
                \"WriteCapacityUnits\": 5
            }
        }
    ]"

# --> List DynamoDb Tables
echo Listing tables ...
aws dynamodb list-tables --endpoint-url="$ENDPOINT_URL"