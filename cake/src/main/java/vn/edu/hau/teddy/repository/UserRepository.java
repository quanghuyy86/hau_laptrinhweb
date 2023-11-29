package vn.edu.hau.cake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hau.cake.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUserName(String userName);
}
