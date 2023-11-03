package vn.edu.hau.cake.service;

import vn.edu.hau.cake.model.User;

public interface UserService {
  User findByUserName(String userName);
}
