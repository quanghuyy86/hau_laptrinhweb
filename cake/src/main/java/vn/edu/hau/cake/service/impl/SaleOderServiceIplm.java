package vn.edu.hau.cake.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.hau.cake.dto.Cart;
import vn.edu.hau.cake.dto.CartItem;
import vn.edu.hau.cake.model.SaleOrder;
import vn.edu.hau.cake.model.SaleOrderProducts;
import vn.edu.hau.cake.repository.ProductRepository;
import vn.edu.hau.cake.repository.SaleOrderRepository;
import vn.edu.hau.cake.service.SaleOderService;

@Service
public class SaleOderServiceIplm implements SaleOderService {
    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public SaleOrder save(SaleOrder saleOrder) {

        return saleOrderRepository.save(saleOrder);
    }

    @Override
    public SaleOrder save(SaleOrder saleOrder, HttpServletRequest request) {

        saleOrder.setCode(String.valueOf(System.currentTimeMillis()));

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        for (CartItem cartItem : cart.getCartItems()) {
            SaleOrderProducts saleOrderProducts = new SaleOrderProducts();

            saleOrderProducts.setQuality(cartItem.getQuanlity());
            saleOrder.setTotal(saleOrderProducts.getSaleOrder().getTotal());

            // sử dụng hàm tiện ích add hoặc remove đới với các quan hệ onetomany
            saleOrder.addSaleOrderProducts(saleOrderProducts);
        }
        return saleOrderRepository.save(saleOrder);
    }
    @Override
    public List<SaleOrder> searchSaleorder(String keyword){
        return saleOrderRepository.searchSaleOrder(keyword);
    }



    @Override
    public Page<SaleOrder> searchPageSaleorder(String keyword, int page, int pageSize){
        List list = this.searchSaleorder(keyword);
        Pageable pageable = PageRequest.of(page, pageSize);
        Integer start = (int)pageable.getOffset();
        Integer end = Math.toIntExact((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);
        return new PageImpl<SaleOrder>(list, pageable, this.searchSaleorder(keyword).size());
    }


    @Override
    public SaleOrder findSaleOrderWithProducts(Integer saleOrderId) {
        Optional<SaleOrder> saleOrder = saleOrderRepository.findById(saleOrderId);
        if (saleOrder.isPresent()) {
            return saleOrder.get();
        }
        return null;
    }

    @Override
    public Page<SaleOrder> gettAllSaleorder(int page, int pageSize){
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return saleOrderRepository.findAll(pageable);
    }

    @Override
    public List<SaleOrder> saveAll(List<SaleOrder> entities) {
        return (List<SaleOrder>) saleOrderRepository.saveAll(entities);
    }

    @Override
    public Optional<SaleOrder> findById(Integer integer) {
        return saleOrderRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return saleOrderRepository.existsById(integer);
    }

    @Override
    public Iterable<SaleOrder> findAll() {
        return saleOrderRepository.findAll();
    }

    @Override
    public Iterable<SaleOrder> findAllById(Iterable<Integer> integers) {
        return saleOrderRepository.findAllById(integers);
    }

    @Override
    public long count() {
        return saleOrderRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        Optional<SaleOrder> saleOrderOptional = saleOrderRepository.findById(integer);
        if (saleOrderOptional.isPresent()) {
            SaleOrder saleOrder = saleOrderOptional.get();
            saleOrder.deleteAllSaleOrderProducts(); // Xóa tất cả SaleOrderProducts liên quan
            saleOrderRepository.deleteById(integer); // Xóa SaleOrder chính
        }
    }


    @Override
    public void delete(SaleOrder entity) {
        entity.deleteAllSaleOrderProducts();
        saleOrderRepository.deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        saleOrderRepository.deleteAllById(integers);
    }

    @Override
    public void deleteAll(Iterable<? extends SaleOrder> entities) {
        saleOrderRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        saleOrderRepository.deleteAll();
    }
}
