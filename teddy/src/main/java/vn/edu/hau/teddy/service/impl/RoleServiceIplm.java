package vn.edu.hau.teddy.service.impl;

import org.springframework.stereotype.Service;
import vn.edu.hau.teddy.model.Role;
import vn.edu.hau.teddy.repository.RoleRepository;
import vn.edu.hau.teddy.service.RoleService;

@Service
public class RoleServiceIplm implements RoleService {


  private final RoleRepository roleRepository;

  public RoleServiceIplm(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findRoleByName(String name) {
    return roleRepository.findByName(name);
  }

  @Override
  public Role save(Role role) {
    return roleRepository.save(role);
  }


}
