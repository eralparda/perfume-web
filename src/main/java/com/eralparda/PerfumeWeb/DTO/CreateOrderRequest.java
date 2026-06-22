package com.eralparda.PerfumeWeb.DTO;


import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private Long userId;
    private List<Long> perfumeIds;
    private List<Integer> quantities;
}
