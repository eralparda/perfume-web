package com.eralparda.PerfumeWeb.Service;

import com.eralparda.PerfumeWeb.DTO.BrandRequest;
import com.eralparda.PerfumeWeb.Entity.Brand;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
import com.eralparda.PerfumeWeb.Repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id){
        return brandRepository.findById(id).
                orElseThrow(()-> new NotFoundException("Brand not found!"));
    }

    public Brand createBrand(BrandRequest request){
        if(request.getId() != null){
            Brand brand = getBrandById(request.getId());
            brand.setName(request.getName());
            return brandRepository.save(brand);
        }
        return brandRepository.save(Brand.toModel(request));
    }

    public void deleteBrand(Long id){
        brandRepository.deleteById(id);
    }
}
