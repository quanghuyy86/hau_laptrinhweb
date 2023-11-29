package vn.edu.hau.cake.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.edu.hau.cake.model.SaleOrder;

public interface SaleOrderRepository extends CrudRepository<SaleOrder, Integer> {
  @Query("SELECT c FROM SaleOrder c WHERE c.code like %?1%")
  List<SaleOrder> searchSaleOrder(String keyword);
  Page<SaleOrder> findAll(Pageable pageable);
}
