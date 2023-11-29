package vn.edu.hau.teddy.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.edu.hau.teddy.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("SELECT c FROM Product c WHERE c.title like %?1%")
    List<Product> searchProduct(String keyword);

    @Query("SELECT p FROM Product p WHERE p.categories.id = 1 and p.isDelete = false")
    List<Product> findProductsInCategory1();

    @Query("SELECT p FROM Product p WHERE p.categories.id = 2 and p.isDelete = false")
    List<Product> findProductsInCategory2();

    @Query("SELECT p FROM Product p WHERE p.categories.id = 3 and p.isDelete = false")
    List<Product> findProductsInCategory3();

    @Query("SELECT p FROM Product p WHERE p.categories.id = 4 and p.isDelete = false")
    List<Product> findProductsInCategory4();

    @Query("SELECT p FROM Product p WHERE p.categories.id = 5 and p.isDelete = false")
    List<Product> findProductsInCategory5();

    Page<Product> findAll(Pageable pageable);


}
