package acs.b3o.controller;

import acs.b3o.dto.request.UserGroupRequest;
import acs.b3o.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService; // 서비스 계층 주입

    @PostMapping("/create")
    public ResponseEntity<?> createUserGroup(@RequestBody UserGroupRequest userGroupRequest, @AuthenticationPrincipal UserDetails userDetails) {
        // 서비스 메서드 호출
        userGroupService.createUserGroup(userGroupRequest);
        return ResponseEntity.ok().build();
    }
}
