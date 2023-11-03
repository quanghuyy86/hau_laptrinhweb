package vn.edu.hau.cake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hau.cake.model.User;
import vn.edu.hau.cake.repository.UserRepository;
@Service
public class UserServiceIplm implements UserService{

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }
}
