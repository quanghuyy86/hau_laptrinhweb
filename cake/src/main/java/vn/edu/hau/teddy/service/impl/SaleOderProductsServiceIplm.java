package vn.edu.hau.cake.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import vn.edu.hau.cake.model.SaleOrderProducts;
import vn.edu.hau.cake.repository.SaleOderProductsRepository;
import vn.edu.hau.cake.service.SaleOderProductsService;

@Service
public class SaleOderProductsServiceIplm implements SaleOderProductsService {

    private final SaleOderProductsRepository saleOderProductsRepository;

    public SaleOderProductsServiceIplm(SaleOderProductsRepository saleOderProductsRepository) {
        this.saleOderProductsRepository = saleOderProductsRepository;
    }

    @Override
    public SaleOrderProducts save(SaleOrderProducts entity) {
        return saleOderProductsRepository.save(entity);
    }

    @Override
    public List<SaleOrderProducts> saveAll(List<SaleOrderProducts> entities) {
        return (List<SaleOrderProducts>) saleOderProductsRepository.saveAll(entities);
    }

    @Override
    public Optional<SaleOrderProducts> findById(Integer integer) {
        return saleOderProductsRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return saleOderProductsRepository.existsById(integer);
    }

    @Override
    public List<SaleOrderProducts> findAll() {
        return (List<SaleOrderProducts>) saleOderProductsRepository.findAll();
    }

    @Override
    public long count() {
        return saleOderProductsRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {

        saleOderProductsRepository.deleteById(integer);
    }


}
