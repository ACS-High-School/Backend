package acs.b3o.dto.response;

import lombok.Builder;
import lombok.Getter;
import acs.b3o.entity.User;

@Getter
@Builder
public class UserGroupResponse {
    private int groupCode;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private String status;
}
