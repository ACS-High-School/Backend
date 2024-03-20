package acs.b3o.controller;

import acs.b3o.dto.response.FederatedResponse;
import acs.b3o.dto.response.InferenceResponse;
import acs.b3o.entity.Federated;
import acs.b3o.service.FederatedService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fl")
public class FederatedController {
    @Autowired
    FederatedService federatedService;

    @GetMapping("/results")
    public ResponseEntity<List<FederatedResponse>> getAllInferenceResults(
        @AuthenticationPrincipal String username) {
        List<FederatedResponse> responses = federatedService.getAllFederatedResults(username);
        return ResponseEntity.ok(responses);
    }
}
