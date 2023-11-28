package vn.edu.hau.cake.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hau.cake.model.Role;
import vn.edu.hau.cake.repository.RoleRepository;
import vn.edu.hau.cake.service.RoleService;

@Service
public class RoleServiceIplm implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role findRoleByName(String name) {
    return roleRepository.findByName(name);
  }

  @Override
  public Role save(Role role) {
    return roleRepository.save(role);
  }


}
