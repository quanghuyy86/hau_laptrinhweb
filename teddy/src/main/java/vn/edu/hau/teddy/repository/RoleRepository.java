package vn.edu.hau.teddy.repository;

import org.springframework.data.repository.CrudRepository;
import vn.edu.hau.teddy.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
  Role findByName(String name);
}
