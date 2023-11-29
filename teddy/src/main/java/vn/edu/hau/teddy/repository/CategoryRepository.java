package vn.edu.hau.teddy.repository;

import org.springframework.data.repository.CrudRepository;
import vn.edu.hau.teddy.model.Categories;

public interface CategoryRepository extends CrudRepository<Categories, Integer> {
}
