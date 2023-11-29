package vn.edu.hau.teddy.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import vn.edu.hau.teddy.model.ProductImages;
import vn.edu.hau.teddy.repository.ProductImageRepository;
import vn.edu.hau.teddy.service.ProductImageService;

@Service
public class ProductImageServiceIplm implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageServiceIplm(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public ProductImages save(ProductImages entity) {
        return productImageRepository.save(entity);
    }

    @Override
    public List<ProductImages> saveAll(List<ProductImages> entities) {
        return (List<ProductImages>)productImageRepository.saveAll(entities);
    }

    @Override
    public Optional<ProductImages> findById(Integer integer) {
        return productImageRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return productImageRepository.existsById(integer);
    }

    @Override
    public List<ProductImages> findAll() {
        return (List<ProductImages>)productImageRepository.findAll();
    }



    @Override
    public void deleteById(Integer integer) {
        productImageRepository.deleteById(integer);
    }


}
