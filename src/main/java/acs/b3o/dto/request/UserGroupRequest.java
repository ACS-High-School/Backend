package acs.b3o.dto.request;

import lombok.Getter;
import lombok.Setter;
import acs.b3o.entity.User;

@Getter
@Setter
public class UserGroupRequest {
    private int groupCode;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private String status;
}
