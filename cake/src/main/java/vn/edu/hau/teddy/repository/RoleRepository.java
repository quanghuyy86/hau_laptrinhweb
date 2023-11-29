package vn.edu.hau.cake.repository;

import org.springframework.data.repository.CrudRepository;
import vn.edu.hau.cake.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
  Role findByName(String name);
}
