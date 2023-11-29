package vn.edu.hau.cake.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import vn.edu.hau.cake.model.Contact;
import vn.edu.hau.cake.model.SaleOrder;

import java.util.List;
import java.util.Optional;

public interface SaleOderService {

    List<SaleOrder> searchSaleorder(String keyword);

    Page<SaleOrder> searchPageSaleorder(String keyword, int page, int pageSize);

    SaleOrder findSaleOrderWithProducts(Integer saleOrderId);

    SaleOrder save(SaleOrder entity);

    SaleOrder save(SaleOrder saleOrder, HttpServletRequest request);

    Page<SaleOrder> gettAllSaleorder(int page, int pageSize);

    List<SaleOrder> saveAll(List<SaleOrder> entities);

    Optional<SaleOrder> findById(Integer integer);

    boolean existsById(Integer integer);

    Iterable<SaleOrder> findAll();

    void deleteById(Integer integer);

}
