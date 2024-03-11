package acs.b3o.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTaskStatusResponse {
    private String username;
    private String taskId;
    private String taskName;
    private String taskStatus; // Task의 상태를 나타내는 필드 (예: 진행 중, 완료 등)
}
