package acs.b3o.service;

import acs.b3o.entity.GroupStatus;
import acs.b3o.repository.GroupStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupStatusService {

    @Autowired
    private GroupStatusRepository repository;

    public void confirmUpload(int groupCode) {
        GroupStatus groupStatus = repository.findById(groupCode)
            .orElseThrow(() -> new RuntimeException("Group not found"));
        groupStatus.setUser1Confirmed(true); // 예시로 user1Confirmed만 업데이트. 실제 로직에 맞게 조정 필요
        repository.save(groupStatus);
    }
}
