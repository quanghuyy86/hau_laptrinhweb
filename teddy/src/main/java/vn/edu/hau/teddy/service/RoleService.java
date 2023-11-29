package vn.edu.hau.teddy.service;

import vn.edu.hau.teddy.model.Role;

public interface RoleService {

  Role findRoleByName(String name);

  Role save(Role role);


}
