package vn.edu.hau.cake.service;

import java.util.List;
import vn.edu.hau.cake.model.User;

public interface UserService {
  User save(User user);
  User findByUserName(String userName);
  List<User> findAll();

}
