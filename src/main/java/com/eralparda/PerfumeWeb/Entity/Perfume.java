package com.eralparda.PerfumeWeb.Entity;

import com.eralparda.PerfumeWeb.DTO.PerfumeRequest;
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
@Table(name = "perfumes")
public class Perfume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String ml;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToMany
    @JoinTable(
            name = "perfume_notes",
            joinColumns = @JoinColumn(name = "perfume_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id")
    )
    private List<Note> notes;

    public static Perfume toModel(PerfumeRequest request) {
        Perfume perfume = new Perfume();
        perfume.setName(request.getName());
        perfume.setDescription(request.getDescription());
        perfume.setPrice(request.getPrice());
        perfume.setStock(request.getStock());
        perfume.setMl(request.getMl());
        return perfume;
    }
}
