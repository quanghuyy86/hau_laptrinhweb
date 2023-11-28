package vn.edu.hau.cake.service;

import vn.edu.hau.cake.model.Role;

public interface RoleService {

  Role findRoleByName(String name);

  Role save(Role role);


}
