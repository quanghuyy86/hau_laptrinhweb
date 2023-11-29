package vn.edu.hau.teddy.service;

import vn.edu.hau.teddy.model.ProductImages;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    ProductImages save(ProductImages entity);

    List<ProductImages> saveAll(List<ProductImages> entities);

    Optional<ProductImages> findById(Integer integer);

    boolean existsById(Integer integer);

    List<ProductImages> findAll();


    void deleteById(Integer integer);

}
