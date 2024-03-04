package acs.b3o.controller;

import acs.b3o.dto.request.GroupCodeRequest;
import acs.b3o.service.GroupStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupStatusController {

    @Autowired
    private GroupStatusService service;

    @PostMapping("/group/confirmUpload")
    public void confirmUpload(@RequestBody GroupCodeRequest request) {
        service.confirmUpload(request.getGroupCode());
    }
}
