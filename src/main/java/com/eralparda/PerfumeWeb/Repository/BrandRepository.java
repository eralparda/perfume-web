package com.eralparda.PerfumeWeb.Repository;

import com.eralparda.PerfumeWeb.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
}
