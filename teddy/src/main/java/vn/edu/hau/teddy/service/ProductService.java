package vn.edu.hau.teddy.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hau.teddy.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {


    @Transactional
    Product save(Product entity, MultipartFile avatar, MultipartFile[] picture) throws IOException;

    Product updateProduct(Integer id, Product product, MultipartFile avatar,
        MultipartFile[] picture) throws IOException;

    List<Product> searchProduct(String keyword);

    Page<Product> gettAllProduct(int page, int pageSize);

    Page<Product> searchPageProduct(String keyword, int page, int pageSize);

    List<Product> findByCategoryId1();

    List<Product> findByCategoryId2();

    List<Product> findByCategoryId3();

    List<Product> findByCategoryId4();

    List<Product> findByCategoryId5();

    List<Product> saveAll(List<Product> entities);

    Optional<Product> findById(Integer integer);

    boolean existsById(Integer integer);

    Iterable<Product> findAll();

    long count();

    Boolean deleteById(Integer integer);
}


