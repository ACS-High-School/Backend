package acs.b3o.repository;

import acs.b3o.entity.FLTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class FLTaskRepository {
    private final DynamoDbTable<FLTask> FLTaskTable;

    public FLTaskRepository(@Autowired DynamoDbEnhancedClient dynamoDbEnhancedClient,
        @Value("${dynamodb.table.name}") String dynamoDBTableName) {
        this.FLTaskTable = dynamoDbEnhancedClient.table(dynamoDBTableName, TableSchema.fromBean(FLTask.class));
    }

    public FLTask getTaskById(String taskName, String taskID) {
        // 파티션 키와 정렬 키를 모두 사용하여 DynamoDB 테이블에서 항목을 가져옵니다.
        Key key = Key.builder().partitionValue(taskName).sortValue(taskID).build();
        return FLTaskTable.getItem(key);
    }




}
