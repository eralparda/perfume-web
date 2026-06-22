package com.eralparda.PerfumeWeb.Entity;

import com.eralparda.PerfumeWeb.DTO.CategoryRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Perfume> perfumes;

    public static Category toModel(CategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setId(request.getId());
        return category;
    }
}
