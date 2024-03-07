package acs.b3o.controller;

import acs.b3o.dto.request.UserGroupRequest;
import acs.b3o.dto.response.UserGroupResponse;
import acs.b3o.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @PostMapping("/create")
    public ResponseEntity<UserGroupResponse> createUserGroup(@RequestBody UserGroupRequest userGroupRequest, @AuthenticationPrincipal String username) {
        UserGroupResponse response = userGroupService.createUserGroup(userGroupRequest, username);
        return ResponseEntity.ok(response); // DTO를 ResponseEntity에 포함시켜 반환
    }

    @PostMapping("/join")
    public ResponseEntity<UserGroupResponse> joinUserGroup(@RequestBody UserGroupRequest userGroupRequest, @AuthenticationPrincipal String username) {
        UserGroupResponse response = userGroupService.joinUserGroup(userGroupRequest, username);

        // 서비스 계층에서 처리된 결과에 따라 적절한 HTTP 응답 반환
        if (response.getMessage().equals("이 그룹은 이미 가득 찼습니다.")) {
            // 그룹이 가득 찼을 경우 409 Conflict 상태 코드 반환
            return ResponseEntity.status(409).body(response);
        } else if (response.getMessage().equals("해당 그룹 코드에 해당하는 그룹이 없습니다.")) {
            // 그룹 코드에 해당하는 그룹이 없을 경우 404 Not Found 상태 코드 반환
            return ResponseEntity.status(404).body(response);
        } else {
            // 성공적으로 추가된 경우 200 OK 상태 코드 사용
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserGroupResponse> getUsers(@RequestBody UserGroupRequest userGroupRequest) {
        UserGroupResponse response = userGroupService.getUsers(userGroupRequest);

        return ResponseEntity.ok(response);
    }
}
