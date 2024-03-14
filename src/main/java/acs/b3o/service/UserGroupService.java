package acs.b3o.service;

import acs.b3o.dto.request.UserGroupRequest;
import acs.b3o.dto.response.UserGroupResponse;
import acs.b3o.dto.response.UserTaskStatusResponse;
import acs.b3o.entity.FLTask;
import acs.b3o.entity.Federated;
import acs.b3o.entity.User;
import acs.b3o.entity.UserGroup;
import acs.b3o.repository.FLTaskRepository;
import acs.b3o.repository.FederatedRepository;
import acs.b3o.repository.UserGroupRepository;
import acs.b3o.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private FederatedRepository federatedRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${api.gateway.url}")
    private String apiUrl;

    @Value("${stepfunctions.statemachine.arn}")
    private String stateMachineArn;


    @Autowired
    private FLTaskRepository flTaskRepository;

    public UserGroupResponse createUserGroup(UserGroupRequest userGroupRequest, String username) {
        User user = userRepository.findByUsername(username);

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupCode(userGroupRequest.getGroupCode());
        userGroup.setUser1(user);

        userGroupRepository.save(userGroup);

        String uuid = UUID.randomUUID().toString();

        // Federated 객체 생성
        Federated federated = Federated.builder()
            .groupCode(userGroup) // 여기서 groupCode는 UserGroup 객체입니다.
            .description(userGroupRequest.getDescription()) // 여기서 description은 String입니다.
            .user1Status("notReady")
            .user2Status("notReady")
            .user3Status("notReady")
            .user4Status("notReady")
            .date(new Date()) // 현재 날짜 설정
            .status("none")
            .taskName("Task-"+ userGroup.getGroupCode() + "-" + uuid)
            .build();

        // Federated 객체 저장
        federatedRepository.save(federated);

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

    public UserGroupResponse getUsers(UserGroupRequest userGroupRequest, String username) {
        UserGroup userGroup = userGroupRepository.findByGroupCode(userGroupRequest.getGroupCode());

        if (userGroup == null) {
            return buildResponse(null, "해당 그룹 코드에 해당하는 그룹이 없습니다.");
        }

        User currentUser = userRepository.findByUsername(username);

        // 현재 사용자가 몇 번째 유저인지 확인
        String clientSuffix = ""; // 사용자 번호를 저장할 변수
        if (username.equals(userGroup.getUser1().getUsername())) {
            clientSuffix = "01";
        } else if (username.equals(userGroup.getUser2().getUsername())) {
            clientSuffix = "02";
        } else if (username.equals(userGroup.getUser3().getUsername())) {
            clientSuffix = "03";
        } else if (username.equals(userGroup.getUser4().getUsername())) {
            clientSuffix = "04";
        }

        // fl-client-{suffix} 문자열 생성
        String spaceNameValue = "fl-client-" + clientSuffix;

        // RestTemplate 준비
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSONObject 또는 Map을 사용하여 요청 본문 생성
        JSONObject requestJson = new JSONObject();
        requestJson.put("space_name", spaceNameValue);

        // 요청 본문과 헤더를 포함하는 HttpEntity 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson.toString(), headers);

        // POST 요청을 보냄
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        // 응답에서 'body' 항목 추출 및 URL 추출
        JSONObject jsonResponse = new JSONObject(response.getBody());

        String body = jsonResponse.getString("body");
        // JSON 형식의 문자열에서 첫번째와 마지막 큰따옴표 제거
        String fetchedUrl = body.substring(1, body.length() - 1);

        List<UserTaskStatusResponse> userTasks = new ArrayList<>();

        // 각 사용자 객체를 리스트로 관리
        List<User> users = Arrays.asList(userGroup.getUser1(), userGroup.getUser2(), userGroup.getUser3(), userGroup.getUser4());

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user != null) {
                // 사용자별 고유한 taskId 생성
                String taskIdSuffix = String.format("000%d00000001", i + 1);
                String taskName = "FL-Task-2024-03-07-05-52-59"; // 고정된 taskName 사용

                // 데이터베이스에서 Task 정보 조회
                FLTask flTask = flTaskRepository.getTaskById(taskName, taskIdSuffix);
                if (flTask != null) {
                    System.out.println(flTask.getTaskId());
                    // Task 정보를 바탕으로 UserTaskStatusDTO 객체 생성
                    // 여기서 taskStatus를 'ready'로 직접 설정합니다.
                    UserTaskStatusResponse userTaskStatusResponse = UserTaskStatusResponse.builder()
                        .username(user.getUsername())
                        .taskId(flTask.getTaskId())
                        .taskName(flTask.getTaskName())
                        .taskStatus("ready") // DTO의 taskStatus를 'ready'로 설정
                        .build();

                    // 생성된 DTO 객체를 리스트에 추가
                    userTasks.add(userTaskStatusResponse);
                }
            }
        }

        Federated federated = federatedRepository.findByGroupCode(userGroup);

        String description = federated.getDescription();


        System.out.println(fetchedUrl);
        System.out.println(userTasks);
        System.out.println(description);


        // UserGroup 엔티티에서 사용자 정보를 추출하여 UserGroupResponse 객체를 생성
        return UserGroupResponse.builder()
            .groupCode(userGroup.getGroupCode())
            .user1(userGroup.getUser1())
            .user2(userGroup.getUser2())
            .user3(userGroup.getUser3())
            .user4(userGroup.getUser4())
            .currentUser(currentUser)
            .jupyterLabUrl(fetchedUrl)
            .userTasks(userTasks)
            .message("사용자 그룹 정보가 성공적으로 검색되었습니다")
            .description(description)
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

    public UserGroupResponse startStateMachine(UserGroupRequest userGroupRequest) {
        UserGroup userGroup = userGroupRepository.findByGroupCode(userGroupRequest.getGroupCode());

        String groupCode = String.valueOf(userGroupRequest.getGroupCode());
        String taskName = "Task_" + groupCode;

        // RestTemplate 준비
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSONObject 또는 Map을 사용하여 요청 본문 생성
        JSONObject requestJson = new JSONObject();
        requestJson.put("name", taskName);
        requestJson.put("stateMachineArn", stateMachineArn);

        // 요청 본문과 헤더를 포함하는 HttpEntity 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson.toString(), headers);

        // POST 요청을 보냄
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/execute", requestEntity, String.class);

        // 응답에서 'body' 항목 추출 및 URL 추출
        JSONObject jsonResponse = new JSONObject(response.getBody());

        System.out.println(jsonResponse);

        return buildResponse(userGroup, "연합 학습 시작 완료");

    }

    private UserGroupResponse buildResponse(UserGroup userGroup, String message) {
        return UserGroupResponse.builder()
            .groupCode(userGroup != null ? userGroup.getGroupCode() : null)
            .user1(userGroup != null && userGroup.getUser1() != null ? userGroup.getUser1() : null)
            .user2(userGroup != null && userGroup.getUser2() != null ? userGroup.getUser2() : null)
            .user3(userGroup != null && userGroup.getUser3() != null ? userGroup.getUser3() : null)
            .user4(userGroup != null && userGroup.getUser4() != null ? userGroup.getUser4() : null)
            .message(message)
            .build();
    }
}