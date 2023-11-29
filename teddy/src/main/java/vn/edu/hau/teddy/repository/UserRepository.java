package vn.edu.hau.teddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hau.teddy.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUserName(String userName);
}
