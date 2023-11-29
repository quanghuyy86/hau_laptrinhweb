package vn.edu.hau.teddy.service;

import vn.edu.hau.teddy.model.User_Role;

public interface UserRoleService {

  void saveUserRole(Long idUser, Long idRole);

  User_Role save(User_Role userRole);

}
