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
        User user = userRepository.findByNickname(username);

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupCode(userGroupRequest.getGroupCode());
        userGroup.setUser1(user);

        userGroupRepository.save(userGroup);

        return UserGroupResponse.builder()
            .groupCode(userGroupRequest.getGroupCode())
            .user1(username)
            .build();
    }

    public UserGroupResponse joinUserGroup(UserGroupRequest userGroupRequest, String username) {
        User user = userRepository.findByNickname(username);

        UserGroup userGroup = userGroupRepository.findByGroupCode(userGroupRequest.getGroupCode());
        String message;

        // 이미 그룹에 가입된 유저인지 확인
        boolean isUserAlreadyInGroup = user.equals(userGroup.getUser1()) ||
            user.equals(userGroup.getUser2()) ||
            user.equals(userGroup.getUser3()) ||
            user.equals(userGroup.getUser4());

        if (isUserAlreadyInGroup) {
            // 이미 가입된 유저인 경우 메시지 설정
            return UserGroupResponse.builder()
                .groupCode(userGroupRequest.getGroupCode())
                .user1(userGroup.getUser1() != null ? userGroup.getUser1().getNickname() : null)
                .user2(userGroup.getUser2() != null ? userGroup.getUser2().getNickname() : null)
                .user3(userGroup.getUser3() != null ? userGroup.getUser3().getNickname() : null)
                .user4(userGroup.getUser4() != null ? userGroup.getUser4().getNickname() : null)
                .message("이미 그룹의 멤버입니다.")
                .build();
        }
        // 그룹을 찾지 못한 경우 처리
        if (userGroup == null) {
            return UserGroupResponse.builder()
                .groupCode(userGroupRequest.getGroupCode())
                .message("해당 그룹 코드에 해당하는 그룹이 없습니다.")
                .build();
        }


        if (userGroup.getUser2() == null) {
            userGroup.setUser2(user);
            message = "사용자가 그룹에 성공적으로 추가되었습니다.";
        } else if (userGroup.getUser3() == null) {
            userGroup.setUser3(user);
            message = "사용자가 그룹에 성공적으로 추가되었습니다.";
        } else if (userGroup.getUser4() == null) {
            userGroup.setUser4(user);
            message = "사용자가 그룹에 성공적으로 추가되었습니다.";
        } else {
            message = "이 그룹은 이미 가득 찼습니다.";
        }

        userGroupRepository.save(userGroup);

        return UserGroupResponse.builder()
            .groupCode(userGroupRequest.getGroupCode())
            .user1(userGroup.getUser1() != null ? userGroup.getUser1().getNickname() : null)
            .user2(userGroup.getUser2() != null ? userGroup.getUser2().getNickname() : null)
            .user3(userGroup.getUser3() != null ? userGroup.getUser3().getNickname() : null)
            .user4(userGroup.getUser4() != null ? userGroup.getUser4().getNickname() : null)
            .message(message)
            .build();
    }
}
