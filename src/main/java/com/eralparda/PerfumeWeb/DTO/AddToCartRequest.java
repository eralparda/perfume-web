package com.eralparda.PerfumeWeb.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private Long perfumeId;
    private int quantity;
}
