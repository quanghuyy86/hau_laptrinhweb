package vn.edu.hau.cake.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.edu.hau.cake.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("SELECT c FROM Product c WHERE c.title like %?1%")
    List<Product> searchProduct(String keyword);

    @Query("SELECT p FROM Product p WHERE p.categories.id = 1")
    List<Product> findProductsInCategory1();

    @Query("SELECT p FROM Product p WHERE p.categories.id = 2")
    List<Product> findProductsInCategory2();

    @Query("SELECT p FROM Product p WHERE p.categories.id = 3")
    List<Product> findProductsInCategory3();

    @Query("SELECT p FROM Product p WHERE p.categories.id = 4")
    List<Product> findProductsInCategory4();

    @Query("SELECT p FROM Product p WHERE p.categories.id = 5")
    List<Product> findProductsInCategory5();


    Page<Product> findAll(Pageable pageable);


}
