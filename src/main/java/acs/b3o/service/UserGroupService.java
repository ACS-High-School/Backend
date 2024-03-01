package acs.b3o.service;

import acs.b3o.dto.request.UserGroupRequest;
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

    public void createUserGroup(UserGroupRequest userGroupRequest, String username) {
        // UserGroup 엔티티 생성 및 설정

        User user = userRepository.findByNickname(username);

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupCode(userGroupRequest.getGroupCode());
        System.out.println(userGroupRequest.getGroupCode());
        userGroup.setUser1(user);

        System.out.println("username : " + username);
        userGroupRepository.save(userGroup); // 데이터베이스에 저장
    }
}

