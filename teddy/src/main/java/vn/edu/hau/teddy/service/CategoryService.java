package vn.edu.hau.teddy.service;

import vn.edu.hau.teddy.model.Categories;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Categories save(Categories entity) throws ClassNotFoundException;

    Categories updateCategory(Integer id, Categories categories);

    List<Categories> saveAll(List<Categories> entities);

    Optional<Categories> findById(Integer integer);

    boolean existsById(Integer integer);

    List<Categories> findAll();


    Boolean deleteById(Integer integer);

    void delete(Categories entity);

}
