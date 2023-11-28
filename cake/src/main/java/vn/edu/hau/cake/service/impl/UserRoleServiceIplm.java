package vn.edu.hau.cake.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hau.cake.model.Role;
import vn.edu.hau.cake.model.User;
import vn.edu.hau.cake.model.User_Role;
import vn.edu.hau.cake.repository.RoleRepository;
import vn.edu.hau.cake.repository.UserRepository;
import vn.edu.hau.cake.repository.UserRoleRepository;
import vn.edu.hau.cake.service.UserRoleService;

@Service
public class UserRoleServiceIplm implements UserRoleService {
  @Autowired
  private UserRoleRepository userRoleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void saveUserRole(Long idUser, Long idRole) {
    User user = userRepository.findById(idUser).orElse(null);
    Role role = roleRepository.findById(Math.toIntExact(idRole)).orElse(null);

    if (user != null && role != null) {
      User_Role userRole = new User_Role();
      userRole.setUser(user);
      userRole.setRole(role);

      userRoleRepository.save(userRole);
    }
  }

  @Override
  public User_Role save(User_Role userRole) {
    return userRoleRepository.save(userRole);
  }
}
