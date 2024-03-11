package acs.b3o.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class FLTask {
    private String taskName;
    private String taskID;
    private String roundId;
    private String memberId;
    private String numSamples;
    private String trainAcc;
    private String testAcc;
    private String trainLoss;
    private String testLoss;
    private String weightsFile;
    private String source;
    private String taskToken;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("Task_Name")
    public String getTaskName() {
        return taskName;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("Task_ID")
    public String getTaskID() {
        return taskID;
    }

    @DynamoDbAttribute("member_ID")
    public String getMemberId() {
        return memberId;
    }

    @DynamoDbAttribute("Round_ID")
    public String getRoundId() {
        return roundId;
    }

    @DynamoDbAttribute("Num_Samples")
    public String getNumSamples() {
        return numSamples;
    }

    @DynamoDbAttribute("Train_Acc")
    public String getTrainAcc() {
        return trainAcc;
    }

    @DynamoDbAttribute("Test_Acc")
    public String getTestAcc() {
        return testAcc;
    }

    @DynamoDbAttribute("Train_Loss")
    public String getTrainLoss() {
        return trainLoss;
    }

    @DynamoDbAttribute("Test_Loss")
    public String getTestLoss() {
        return testLoss;
    }

    @DynamoDbAttribute("Weights_File")
    public String getWeightsFile() {
        return weightsFile;
    }

    @DynamoDbAttribute("Source")
    public String getSource() {
        return source;
    }

    @DynamoDbAttribute("Task_Token")
    public String getTaskToken() {
        return taskToken;
    }


}
