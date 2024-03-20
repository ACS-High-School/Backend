package acs.b3o.service;

import acs.b3o.dto.response.FederatedResponse;
import acs.b3o.dto.response.InferenceResponse;
import acs.b3o.entity.Federated;
import acs.b3o.entity.Inference;
import acs.b3o.entity.User;
import acs.b3o.entity.UserGroup;
import acs.b3o.repository.FederatedRepository;
import acs.b3o.repository.InferenceRepository;
import acs.b3o.repository.UserGroupRepository;
import acs.b3o.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FederatedService {

    @Autowired
    private FederatedRepository federatedRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<FederatedResponse> getAllFederatedResults(String username) {

        User user = userRepository.findByUsername(username);

        // 사용자가 속한 모든 그룹을 찾습니다.
        List<UserGroup> userGroups = userGroupRepository.findByUser(user);

        // 그룹 코드 목록을 추출합니다.
        List<Integer> groupCodes = userGroups.stream()
            .map(UserGroup::getGroupCode)
            .toList();

        // 해당 그룹 코드에 속하는 Federated 결과를 모두 찾습니다.
        List<Federated> federateds = federatedRepository.findAllByGroupCodes(groupCodes);

        // Federated 엔티티를 FederatedResponse DTO로 변환합니다.
        return federateds.stream().map(federated ->
            FederatedResponse.builder()
                .taskName(federated.getTaskName())
                .date(federated.getDate())
                .description(federated.getDescription())
                .model(federated.getModel())
                .status(federated.getStatus())
                .groupCode(federated.getGroupCode().getGroupCode())
                .user1Status(federated.getUser1Status())
                .user2Status(federated.getUser2Status())
                .user3Status(federated.getUser3Status())
                .user4Status(federated.getUser4Status())
                .result(federated.getTaskName() + "-output.npy") // "result"는 "taskName"과 매핑되어야 한다고 가정합니다.
                .build()
        ).collect(Collectors.toList());
    }
}
