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
    public Categories save(Categories entity) throws ClassNotFoundException {
        if(entity.getName() != null && !entity.getName().isEmpty()){
            return categoryRepository.save(entity);
        }
        throw new ClassNotFoundException("Không nhập tên category");
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
        return categoryRepository.getCategories();
    }

    @Override
    public Boolean deleteById(Integer id) {
        Optional<Categories> categories = categoryRepository.findById(id);
        if(categories.isPresent()){
            categories.get().setDelete(true);
            categoryRepository.save(categories.get());
        }
        else{
            return null;
        }
        return false;
    }

    @Override
    public void delete(Categories entity) {
        categoryRepository.delete(entity);
    }

}
