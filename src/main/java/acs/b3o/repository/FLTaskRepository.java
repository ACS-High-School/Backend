package acs.b3o.repository;

import acs.b3o.entity.FLTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class FLTaskRepository {
    private final DynamoDbTable<FLTask> FLTaskTable;

    @Value("${dynamodb.table.name}")
    private String dynamoDBTableName;
    public FLTaskRepository(@Autowired DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.FLTaskTable = dynamoDbEnhancedClient.table(dynamoDBTableName, TableSchema.fromBean(FLTask.class));
    }



}
