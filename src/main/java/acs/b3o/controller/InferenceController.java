package acs.b3o.controller;

import acs.b3o.dto.request.InferenceRequest;
import acs.b3o.dto.response.InferenceResponse;
import acs.b3o.service.InferenceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inference")
public class InferenceController {

  @Autowired
  InferenceService inferenceService;

  @PostMapping(value = "/upload")
  public ResponseEntity<InferenceResponse> uploadInferenceData(
      @RequestParam("model") String model,
      @RequestParam("taskTitle") String title,
      @AuthenticationPrincipal String username) {
    InferenceResponse response = inferenceService.createInference(model, title, username);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/results")
  public ResponseEntity<List<InferenceResponse>> getAllInferenceResults(
      @AuthenticationPrincipal String username) {
    List<InferenceResponse> responses = inferenceService.getAllInferenceResults(username);
    return ResponseEntity.ok(responses);
  }


}
