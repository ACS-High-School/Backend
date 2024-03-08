package acs.b3o.controller;

import acs.b3o.entity.User;
import acs.b3o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  // Get the authenticated user's information
  @GetMapping
  public ResponseEntity<User> getAuthenticatedUserInfo(@AuthenticationPrincipal String nickname) {
    User user = userService.getUserInfo(nickname);
    if (user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.notFound().build();
  }


  // Update the authenticated user's company
  @PutMapping("/company")
  public ResponseEntity<Void> updateUserCompany(@AuthenticationPrincipal String username,
      @RequestBody String newCompany) {
    boolean updated = userService.updateUserCompany(username, newCompany);
    if (updated) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }


}