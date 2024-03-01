package acs.b3o.service;

import acs.b3o.dto.request.UserGroupRequest;
import acs.b3o.entity.UserGroup;
import acs.b3o.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    public void createUserGroup(UserGroupRequest userGroupRequest) {
        // UserGroup 엔티티 생성 및 설정
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupCode(userGroupRequest.getGroupCode());
        // 유저 정보 설정, 예를 들어 userGroup.setUser1(username); 등
        // ...
//        userGroup.setUser1(username);
        userGroupRepository.save(userGroup); // 데이터베이스에 저장
    }
}

