package com.eralparda.PerfumeWeb.Service;

import com.eralparda.PerfumeWeb.DTO.CategoryRequest;
import com.eralparda.PerfumeWeb.Entity.Category;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
import com.eralparda.PerfumeWeb.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Category not found with id! " + id));
    }

    public Category createCategory(CategoryRequest request){
        if(request.getId() != null) {
            Category category = getCategoryById(request.getId());
            category.setName(request.getName());
            return categoryRepository.save(category);
        }
        return categoryRepository.save(Category.toModel(request));
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
