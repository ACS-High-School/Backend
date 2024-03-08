package acs.b3o.service;

import acs.b3o.entity.User;
import acs.b3o.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

  @Autowired
  private UserRepository userRepository;

  // Retrieve user information by email
  public User getUserInfo(String userName) {
    return userRepository.findByUsername(userName);
  }

  // 회사명 업데이트
  public boolean updateUserCompany(String userName, String newCompany) {
    User user = userRepository.findByUsername(userName);
    if (user != null) {
      user.setCompany(newCompany);
      userRepository.save(user);
      return true;
    }
    return false;
  }


}
