package com.eralparda.PerfumeWeb.Controller;

import com.eralparda.PerfumeWeb.DTO.BrandRequest;
import com.eralparda.PerfumeWeb.Entity.Brand;
import com.eralparda.PerfumeWeb.Service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public List<Brand> getAllBrands(){
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable Long id){
        return brandService.getBrandById(id);
    }

    @PostMapping
    public Brand createBrand(@RequestBody BrandRequest request){
        return brandService.createBrand(request);
    }

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id){
        brandService.deleteBrand(id);
    }
}
