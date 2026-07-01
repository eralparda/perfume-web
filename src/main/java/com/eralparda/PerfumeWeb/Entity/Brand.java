package com.eralparda.PerfumeWeb.Entity;

import com.eralparda.PerfumeWeb.DTO.BrandRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private List<Perfume> perfumes;

    public static Brand toModel(BrandRequest request){
        Brand brand = new Brand();
        brand.setName(request.getName());
        return brand;
    }
}
