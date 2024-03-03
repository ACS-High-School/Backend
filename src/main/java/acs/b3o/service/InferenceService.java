package acs.b3o.service;

import acs.b3o.dto.request.InferenceRequest;
import acs.b3o.dto.response.InferenceResponse;
import acs.b3o.entity.Inference;
import acs.b3o.entity.User;
import acs.b3o.repository.InferenceRepository;
import acs.b3o.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InferenceService {

  @Autowired
  private InferenceRepository inferenceRepository;

  @Autowired
  private UserRepository userRepository;

  public InferenceResponse createInference(String model, String title, String username) {
    User user = userRepository.findByNickname(username);

    Inference inference = new Inference();
    inference.setModel(model);
    inference.setTitle(title);
    inference.setInput1(title + "_x1");
    inference.setInput2(title + "_x2");
    inference.setStats("start");
    inference.setDate(new Date());
    inference.setNickname(user);

    inferenceRepository.save(inference);

    return buildResponse(inference, "Inference data가 저장되었습니다.");
  }

  public List<InferenceResponse> getAllInferenceResults(String username) {
    User user = userRepository.findByNickname(username);
    List<Inference> inferences = inferenceRepository.findAllByNickname(user);

    return inferences.stream().map(inference ->
        InferenceResponse.builder()
            .model(inference.getModel())
            .title(inference.getTitle())
            .input1(inference.getInput1())
            .input2(inference.getInput2())
            .result(inference.getResult())
            .stats(inference.getStats())
            .date(inference.getDate())
            .build()
    ).collect(Collectors.toList());
  }

  private InferenceResponse buildResponse(Inference inference, String message) {
    return InferenceResponse.builder()
        .model(inference.getModel())
        .title(inference.getTitle())
        .input1(inference.getInput1())
        .input2(inference.getInput2())
        .date(inference.getDate())
        .stats(inference.getStats())
        .nickname(inference.getNickname().getNickname())
        .message(message)
        .build();
  }

}
