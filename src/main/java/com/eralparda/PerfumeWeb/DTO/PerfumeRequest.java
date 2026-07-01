package com.eralparda.PerfumeWeb.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PerfumeRequest {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String ml;
    private Long categoryId;
    private Long brandId;
    private List<Long> notesIds;
}
