package com.eralparda.PerfumeWeb.Service;

import com.eralparda.PerfumeWeb.DTO.PerfumeRequest;
import com.eralparda.PerfumeWeb.Entity.Brand;
import com.eralparda.PerfumeWeb.Entity.Category;
import com.eralparda.PerfumeWeb.Entity.Note;
import com.eralparda.PerfumeWeb.Entity.Perfume;
import com.eralparda.PerfumeWeb.Exception.BadRequestException;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
import com.eralparda.PerfumeWeb.Repository.BrandRepository;
import com.eralparda.PerfumeWeb.Repository.CategoryRepository;
import com.eralparda.PerfumeWeb.Repository.NoteRepository;
import com.eralparda.PerfumeWeb.Repository.PerfumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;
    private final CategoryRepository categoryRepository;
    private final NoteRepository noteRepository;
    private final BrandRepository brandRepository;

    public List<Perfume> getAllPerfumes(){
        return perfumeRepository.findAll();
    }

    public Perfume getPerfumeById(Long id){
        return perfumeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Perfume not found with id: " + id));
    }

    public Perfume createPerfume(PerfumeRequest request){
        Perfume perfume;
        if(request.getId() != null) {
            perfume = getPerfumeById(request.getId());
            perfume.setName(request.getName());
            perfume.setPrice(request.getPrice());
            perfume.setMl(request.getMl());
            perfume.setStock(request.getStock());
        }else{
            perfume = Perfume.toModel(request);
        }

        if(request.getCategoryId() != null){
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            perfume.setCategory(category);
        }

        if(request.getBrandId() != null){
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new NotFoundException("Brand not found"));
            perfume.setBrand(brand);
        }

        if(request.getNotesIds() != null && !request.getNotesIds().isEmpty()){
            List<Note> notes = request.getNotesIds().stream()
                    .map(note -> noteRepository.findById(note)
                            .orElseThrow(() -> new NotFoundException("Note not found")))
                    .collect(Collectors.toList());
            perfume.setNotes(notes);
        }

        return perfumeRepository.save(perfume);
    }

    public void deletePerfume(Long id){
        perfumeRepository.deleteById(id);
    }

    public List<Perfume> searchByName(String name){
        return perfumeRepository.findByNameContaining(name);
    }

    public List<Perfume> getByCategory(Long categoryId){
        return perfumeRepository.findByCategory_Id(categoryId);
    }

    public List<Perfume> getByBrand(Long brandId){
        return perfumeRepository.findByBrand_Id(brandId);
    }

    public List<Perfume> getByNote(Long noteId){
        return perfumeRepository.findByNotes_Id(noteId);
    }

    public void decreaseStock(Long perfumeId, int quantity){
        Perfume perfume = getPerfumeById(perfumeId);
        if(perfume.getStock() < quantity){
            throw new BadRequestException("Yeterli stok yok!");
        }
        perfume.setStock(perfume.getStock() - quantity);
        perfumeRepository.save(perfume);
    }

    public boolean checkStock(Long perfumeId, int quantity){
        Perfume perfume = getPerfumeById(perfumeId);
        return perfume.getStock() >= quantity;
    }
}
