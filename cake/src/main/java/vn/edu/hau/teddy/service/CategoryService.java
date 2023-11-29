package vn.edu.hau.cake.service;

import vn.edu.hau.cake.model.Categories;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Categories save(Categories entity);

    Categories updateCategory(Integer id, Categories categories);

    List<Categories> saveAll(List<Categories> entities);

    Optional<Categories> findById(Integer integer);

    boolean existsById(Integer integer);

    List<Categories> findAll();


    void deleteById(Integer integer);

    void delete(Categories entity);

}
