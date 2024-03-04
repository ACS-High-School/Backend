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

  // Update the authenticated user's name
  @PutMapping("/name")
  public ResponseEntity<Void> updateUserName(@AuthenticationPrincipal String nickname,
      @RequestBody String newName) {
    boolean updated = userService.updateUserName(nickname, newName);
    if (updated) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  // Update the authenticated user's company
  @PutMapping("/company")
  public ResponseEntity<Void> updateUserCompany(@AuthenticationPrincipal String nickname,
      @RequestBody String newCompany) {
    boolean updated = userService.updateUserCompany(nickname, newCompany);
    if (updated) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  // This endpoint might need a different approach since it changes the username/nickname,
  // which is also used for authentication. Consider the implications and handle accordingly.
  @PutMapping("/nickname")
  public ResponseEntity<Void> updateUserNickname(@AuthenticationPrincipal String oldNickname,
      @RequestBody String newNickname) {
    boolean updated = userService.updateUserNickname(oldNickname, newNickname);
    if (updated) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }
}