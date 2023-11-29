package vn.edu.hau.teddy.service;

import java.util.List;
import vn.edu.hau.teddy.model.User;

public interface UserService {
  User save(User user);
  User findByUserName(String userName);
  List<User> findAll();

}
