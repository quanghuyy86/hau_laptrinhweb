package vn.edu.hau.cake.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import vn.edu.hau.cake.model.User;
import vn.edu.hau.cake.repository.UserRepository;
import vn.edu.hau.cake.service.UserService;

@Service
public class UserServiceIplm implements UserService {


  private final UserRepository userRepository;

  public UserServiceIplm(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public User findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }
}
