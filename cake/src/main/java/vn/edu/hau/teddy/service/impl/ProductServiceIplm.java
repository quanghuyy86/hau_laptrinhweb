package vn.edu.hau.cake.service.impl;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;
import vn.edu.hau.cake.model.Product;
import vn.edu.hau.cake.model.ProductImages;
import vn.edu.hau.cake.repository.ProductRepository;
import vn.edu.hau.cake.service.ProductService;

@Service
public class ProductServiceIplm implements ProductService {

    private final ProductRepository productRepository;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads/";

    public ProductServiceIplm(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private boolean isEmptyUploadFile(MultipartFile image){
        return image == null || image.getOriginalFilename().isEmpty();
    }

    private boolean isEmptyUploadFile(MultipartFile[] image){
        if(image == null || image.length <= 0){
            return true;
        }
        if(image.length == 1 && image[0].getOriginalFilename().isEmpty()){
            return true;
        }
        return false;
    }

    private String getUniqueUploadFileName(String fileName) { //hàm dùng để nếu trùng tên ảnh thì gen ra tên ảnh khác biệt
        String[] splitFileName = fileName.split("\\.");
        return splitFileName[0] + System.currentTimeMillis() + "." + splitFileName[1];
    }
    @Override
    @Transactional
    public Product save(Product entity, MultipartFile avatar, MultipartFile[] picture) throws IOException {
        HtmlUtils.htmlEscape(entity.getDetail());
        HtmlUtils.htmlEscape(entity.getShortDes());
        if(!isEmptyUploadFile(avatar)){
            Path filaeNameAndPath = Paths.get(UPLOAD_DIRECTORY, avatar.getOriginalFilename());
            Files.write(filaeNameAndPath, avatar.getBytes());
            entity.setAvatar("/uploads/" + avatar.getOriginalFilename());
        }
        if(!isEmptyUploadFile(picture)){
            for (MultipartFile pic : picture){
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, pic.getOriginalFilename());
                Files.write(fileNameAndPath, pic.getBytes());

                ProductImages productImages = new ProductImages();
                productImages.setPath("/uploads/" + pic.getOriginalFilename());
                productImages.setTitle(pic.getOriginalFilename());
                entity.addProductImages(productImages);
            }
        }
        return productRepository.save(entity);
    }
    @Override
    @Transactional
    public Product updateProduct(Integer id,Product product, MultipartFile avatar, MultipartFile[] picture) throws IOException{
        if(product != null){
            Optional<Product> product1 = productRepository.findById(id);
            if(product1 != null){
                product1.get().setTitle(product.getTitle());
                product1.get().setPrice(product.getPrice());
                product1.get().setPriceSale(product.getPriceSale());
                product1.get().setDetail(HtmlUtils.htmlEscape(product.getDetail()));
                product1.get().setShortDes(HtmlUtils.htmlEscape(product.getShortDes()));
                product1.get().setCategories(product.getCategories());
                product1.get().setStatus(product.getStatus());

                if(!isEmptyUploadFile(avatar)){
                    new File(System.getProperty("user.dir") + product1.get().getAvatar()).delete();

                    String fileName = avatar.getOriginalFilename();
                    avatar.transferTo(new File(UPLOAD_DIRECTORY + fileName));
                    product1.get().setAvatar("/uploads/"+fileName);
                }

                if(!isEmptyUploadFile(avatar)){
                    if(product1.get().getProductImages() != null && product1.get().getProductImages().size() > 0){
                        for (ProductImages opi : product1.get().getProductImages()){
                            new File(System.getProperty("user.dir") + product1.get().getAvatar()).delete();
                        }
                    }
                    for (MultipartFile pic : picture){
                        String fileName = pic.getOriginalFilename();

                        pic.transferTo(new File(UPLOAD_DIRECTORY + fileName));

                        ProductImages pi = new ProductImages();
                        pi.setPath("/uploads/" + fileName);
                        pi.setTitle(fileName);
                        product.addProductImages(pi);
                    }
                }
            return productRepository.save(product1.get());

            }
        }
        return null;
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        return productRepository.searchProduct(keyword);
    }

    @Override
    public Page<Product> gettAllProduct(int page, int pageSize) {
//        Pageable pageable = PageRequest.of(page, pageSize);
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchPageProduct(String keyword, int page, int pageSize) {
        List list = this.searchProduct(keyword);

        Pageable pageable = PageRequest.of(page, pageSize);
        Integer start = (int)pageable.getOffset();
        Integer end = Math.toIntExact((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);
        return new PageImpl<Product>(list, pageable, this.searchProduct(keyword).size());
    }

    @Override
    public List<Product> findByCategoryId1(){
        return productRepository.findProductsInCategory1();
    }

    @Override
    public List<Product> findByCategoryId2(){
        return productRepository.findProductsInCategory2();
    }

    @Override
    public List<Product> findByCategoryId3(){
        return productRepository.findProductsInCategory3();
    }

    @Override
    public List<Product> findByCategoryId4() {
        return productRepository.findProductsInCategory4();
    }

    @Override
    public List<Product> findByCategoryId5() {
        return productRepository.findProductsInCategory5();
    }


    @Override
    public List<Product> saveAll(List<Product> entities) {
        return (List<Product>)productRepository.saveAll(entities);
    }

    @Override
    public Optional<Product> findById(Integer integer) {
        return productRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return productRepository.existsById(integer);
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public void deleteById(Integer id) {
        Boolean exists = productRepository.existsById(id);
        if(exists){
            productRepository.deleteById(id);
        }
    }


}
