package com.eralparda.PerfumeWeb.Controller;

import com.eralparda.PerfumeWeb.Entity.Perfume;
import com.eralparda.PerfumeWeb.Service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfumes")
@RequiredArgsConstructor
public class PerfumeController {

    private final PerfumeService perfumeService;

    @GetMapping
    public List<Perfume> getAllPerfumes(){
        return perfumeService.getAllPerfumes();
    }

    @GetMapping("/{id}")
    public Perfume getPerfumeById(@PathVariable Long id){
        return perfumeService.getPerfumeById(id);
    }

    @PostMapping
    public Perfume createPerfume(@RequestBody Perfume perfume){
        return perfumeService.createPerfume(perfume);
    }

    @PutMapping("/{id}")
    public Perfume updatePerfume(@PathVariable Long id,@RequestBody Perfume updatedPerfume){
        return perfumeService.updatePerfume(id,updatedPerfume);
    }

    @DeleteMapping("/{id}")
    public void deletePerfume(@PathVariable Long id){
        perfumeService.deletePerfume(id);
    }

    @GetMapping("/search")
    public List<Perfume> searchByName(@RequestParam String name){
        return perfumeService.searchByName(name);
    }

    @GetMapping("/category/{categoryId}")
    public List<Perfume> getByCategory(@PathVariable Long categoryId){
        return perfumeService.getByCategory(categoryId);
    }

    @GetMapping("/{perfumeId}/stock/{quantity}")
    public boolean checkStock(@PathVariable Long perfumeId,@PathVariable int quantity){
        return perfumeService.checkStock(perfumeId,quantity);
    }
}
