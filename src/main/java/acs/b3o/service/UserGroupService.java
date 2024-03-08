package acs.b3o.service;

import acs.b3o.dto.request.UserGroupRequest;
import acs.b3o.dto.response.UserGroupResponse;
import acs.b3o.entity.User;
import acs.b3o.entity.UserGroup;
import acs.b3o.repository.UserGroupRepository;
import acs.b3o.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    public UserGroupResponse createUserGroup(UserGroupRequest userGroupRequest, String username) {
        User user = userRepository.findByUsername(username);

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupCode(userGroupRequest.getGroupCode());
        userGroup.setUser1(user);

        userGroupRepository.save(userGroup);

        return buildResponse(userGroup, "그룹이 생성되었습니다.");
    }

    public UserGroupResponse joinUserGroup(UserGroupRequest userGroupRequest, String username) {
        User user = userRepository.findByUsername(username);
        UserGroup userGroup = userGroupRepository.findByGroupCode(userGroupRequest.getGroupCode());

        if (userGroup == null) {
            return buildResponse(null, "해당 그룹 코드에 해당하는 그룹이 없습니다.");
        }

        if (isUserInGroup(user, userGroup)) {
            return buildResponse(userGroup, "이미 그룹의 멤버입니다.");
        }

        String message = addUserToGroup(user, userGroup);
        userGroupRepository.save(userGroup);

        return buildResponse(userGroup, message);
    }

    public UserGroupResponse getUsers(UserGroupRequest userGroupRequest) {
        UserGroup userGroup = userGroupRepository.findByGroupCode(userGroupRequest.getGroupCode());

        if (userGroup == null) {
            return buildResponse(null, "해당 그룹 코드에 해당하는 그룹이 없습니다.");
        }

        // UserGroup 엔티티에서 사용자 정보를 추출하여 UserGroupResponse 객체를 생성
        return UserGroupResponse.builder()
            .groupCode(userGroup.getGroupCode())
            .user1(userGroup.getUser1().getUsername())
            .user2(userGroup.getUser2().getUsername())
            .user3(userGroup.getUser3().getUsername())
            .user4(userGroup.getUser4().getUsername())
            .message("사용자 그룹 정보가 성공적으로 검색되었습니다")
            .build();
    }

    private boolean isUserInGroup(User user, UserGroup userGroup) {
        return user.equals(userGroup.getUser1()) || user.equals(userGroup.getUser2()) ||
            user.equals(userGroup.getUser3()) || user.equals(userGroup.getUser4());
    }

    private String addUserToGroup(User user, UserGroup userGroup) {
        if (userGroup.getUser2() == null) {
            userGroup.setUser2(user);
        } else if (userGroup.getUser3() == null) {
            userGroup.setUser3(user);
        } else if (userGroup.getUser4() == null) {
            userGroup.setUser4(user);
        } else {
            return "이 그룹은 이미 가득 찼습니다.";
        }
        return "사용자가 그룹에 성공적으로 추가되었습니다.";
    }

    private UserGroupResponse buildResponse(UserGroup userGroup, String message) {
        return UserGroupResponse.builder()
            .groupCode(userGroup != null ? userGroup.getGroupCode() : null)
            .user1(userGroup != null && userGroup.getUser1() != null ? userGroup.getUser1().getUsername() : null)
            .user2(userGroup != null && userGroup.getUser2() != null ? userGroup.getUser2().getUsername() : null)
            .user3(userGroup != null && userGroup.getUser3() != null ? userGroup.getUser3().getUsername() : null)
            .user4(userGroup != null && userGroup.getUser4() != null ? userGroup.getUser4().getUsername() : null)
            .message(message)
            .build();
    }
}