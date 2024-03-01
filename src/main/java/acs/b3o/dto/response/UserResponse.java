package acs.b3o.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String nickname;
    private String name;
    private String email;
    private String company;
}
