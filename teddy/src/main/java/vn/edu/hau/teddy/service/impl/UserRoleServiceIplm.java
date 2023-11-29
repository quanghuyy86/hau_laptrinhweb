package vn.edu.hau.teddy.service.impl;

import org.springframework.stereotype.Service;
import vn.edu.hau.teddy.model.Role;
import vn.edu.hau.teddy.model.User;
import vn.edu.hau.teddy.model.User_Role;
import vn.edu.hau.teddy.repository.RoleRepository;
import vn.edu.hau.teddy.repository.UserRepository;
import vn.edu.hau.teddy.repository.UserRoleRepository;
import vn.edu.hau.teddy.service.UserRoleService;

@Service
public class UserRoleServiceIplm implements UserRoleService {

  private final UserRoleRepository userRoleRepository;


  private final UserRepository userRepository;


  private final RoleRepository roleRepository;

  public UserRoleServiceIplm(UserRoleRepository userRoleRepository, UserRepository userRepository,
      RoleRepository roleRepository) {
    this.userRoleRepository = userRoleRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

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
