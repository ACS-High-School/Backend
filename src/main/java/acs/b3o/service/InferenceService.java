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
import java.util.Optional;
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
    User user = userRepository.findByUsername(username);

    Inference inference = new Inference();
    inference.setModel(model);
    inference.setTitle(title);
    inference.setInput(username + "-" + title + "-" + model + "-input.csv");
    inference.setStats("start");
    inference.setDate(new Date());
    inference.setUser(user);

    inferenceRepository.save(inference);

    return buildResponse(inference, "Inference data가 저장되었습니다.");
  }

  public List<InferenceResponse> getAllInferenceResults(String username) {
    User user = userRepository.findByUsername(username);
    List<Inference> inferences = inferenceRepository.findAllByUser(user);

    return inferences.stream().map(inference ->
        InferenceResponse.builder()
            .model(inference.getModel())
            .title(inference.getTitle())
            .result(inference.getResult())
            .input(inference.getInput())
            .stats(inference.getStats())
            .date(inference.getDate())
            .user(inference.getUser())
            .build()
    ).collect(Collectors.toList());
  }

  public InferenceResponse getInferenceById(Integer id) {
    Optional<Inference> optionalInference = inferenceRepository.findById(id);
    Inference inference = optionalInference.get();
    return buildResponse(inference, "Inference data가 조회되었습니다.");
  }


  private InferenceResponse buildResponse(Inference inference, String message) {
    return InferenceResponse.builder()
        ._id(inference.get_id())
        .model(inference.getModel())
        .title(inference.getTitle())
        .date(inference.getDate())
        .input(inference.getInput())
        .stats(inference.getStats())
        .user(inference.getUser())
        .result(inference.getResult())
        .message(message)
        .build();
  }

}
