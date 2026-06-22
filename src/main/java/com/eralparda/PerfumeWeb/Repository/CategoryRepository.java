package com.eralparda.PerfumeWeb.Repository;

import com.eralparda.PerfumeWeb.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
