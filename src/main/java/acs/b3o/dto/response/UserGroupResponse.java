package acs.b3o.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserGroupResponse {
    private int groupCode;
    private String user1;
    private String user2;
    private String user3;
    private String user4;
}
