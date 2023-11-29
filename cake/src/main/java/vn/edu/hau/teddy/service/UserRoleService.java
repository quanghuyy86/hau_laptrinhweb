package vn.edu.hau.cake.service;

import vn.edu.hau.cake.model.User_Role;

public interface UserRoleService {

  void saveUserRole(Long idUser, Long idRole);

  User_Role save(User_Role userRole);

}
