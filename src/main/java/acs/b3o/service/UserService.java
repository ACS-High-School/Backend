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
  public User getUserInfo(String nickName) {
    return userRepository.findByNickname(nickName);
  }

  public boolean updateUserName(String nickName, String newName) {
    User user = userRepository.findByNickname(nickName);
    if (user != null) {
      user.setName(newName);
      userRepository.save(user);
      return true;
    }
    return false;
  }

  // 회사명 업데이트
  public boolean updateUserCompany(String nickName, String newCompany) {
    User user = userRepository.findByNickname(nickName);
    if (user != null) {
      user.setCompany(newCompany);
      userRepository.save(user);
      return true;
    }
    return false;
  }

  // 닉네임 업데이트
  public boolean updateUserNickname(String nickName, String newNickname) {
    User user = userRepository.findByNickname(nickName);
    if (user != null) {
      user.setNickname(newNickname);
      userRepository.save(user);
      return true;
    }
    return false;
  }
}
