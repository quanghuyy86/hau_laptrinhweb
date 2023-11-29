package vn.edu.hau.cake.service;

import vn.edu.hau.cake.model.SaleOrder;
import vn.edu.hau.cake.model.SaleOrderProducts;

import java.util.List;
import java.util.Optional;

public interface SaleOderProductsService {
    SaleOrderProducts save(SaleOrderProducts entity);

    List<SaleOrderProducts> saveAll(List<SaleOrderProducts> entities);

    Optional<SaleOrderProducts> findById(Integer integer);

    boolean existsById(Integer integer);

    List<SaleOrderProducts> findAll();

    long count();

    void deleteById(Integer integer);


}
