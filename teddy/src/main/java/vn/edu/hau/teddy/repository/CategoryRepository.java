package vn.edu.hau.teddy.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.edu.hau.teddy.model.Categories;

public interface CategoryRepository extends CrudRepository<Categories, Integer> {
  @Query("SELECT p FROM Categories p WHERE p.isDelete = false")
  List<Categories> getCategories();
}
