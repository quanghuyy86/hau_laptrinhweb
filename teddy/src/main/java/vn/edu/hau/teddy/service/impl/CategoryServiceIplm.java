package vn.edu.hau.teddy.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import vn.edu.hau.teddy.model.Categories;
import vn.edu.hau.teddy.repository.CategoryRepository;
import vn.edu.hau.teddy.service.CategoryService;

@Service
public class CategoryServiceIplm implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceIplm(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Categories save(Categories entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Categories updateCategory(Integer id, Categories categories) {
        if(categories != null){
            Optional<Categories> categories1 = categoryRepository.findById(id);
            categories1.get().setName(categories.getName());
            categories1.get().setDescription(categories.getDescription());
            return categoryRepository.save(categories1.get());
        }
        return null;
    }

    @Override
    public List<Categories> saveAll(List<Categories> entities) {
        return (List<Categories>)categoryRepository.saveAll(entities);
    }

    @Override
    public Optional<Categories> findById(Integer integer) {
        return categoryRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return categoryRepository.existsById(integer);
    }

    @Override
    public List<Categories> findAll() {
        return (List<Categories>) categoryRepository.findAll();
    }

    @Override
    public void deleteById(Integer integer) {
        categoryRepository.deleteById(integer);
    }

    @Override
    public void delete(Categories entity) {
        categoryRepository.delete(entity);
    }

}
