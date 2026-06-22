package com.eralparda.PerfumeWeb.Repository;

import com.eralparda.PerfumeWeb.Entity.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume,Long> {
    List<Perfume> findByNameContaining(String name);
    List<Perfume> findByCategory_Id(Long categoryId);
}
