package com.eralparda.PerfumeWeb.Service;

import com.eralparda.PerfumeWeb.Entity.Category;
import com.eralparda.PerfumeWeb.Entity.Note;
import com.eralparda.PerfumeWeb.Entity.Perfume;
import com.eralparda.PerfumeWeb.Exception.BadRequestException;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
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

    public List<Perfume> getAllPerfumes(){
        return perfumeRepository.findAll();
    }

    public Perfume getPerfumeById(Long id){
        return perfumeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Perfume not found with id: " + id));
    }

    public Perfume createPerfume(Perfume perfume){
        // Gelen category id'si ile DB'den tam Category nesnesini çek
        if(perfume.getCategory() != null && perfume.getCategory().getId() != null){
            Category category = categoryRepository.findById(perfume.getCategory().getId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            perfume.setCategory(category);
        }

        // Gelen note id'leri ile DB'den tam Note nesnelerini çek
        if(perfume.getNotes() != null && !perfume.getNotes().isEmpty()){
            List<Note> notes = perfume.getNotes().stream()
                    .map(note -> noteRepository.findById(note.getId())
                            .orElseThrow(() -> new NotFoundException("Note not found")))
                    .collect(Collectors.toList());
            perfume.setNotes(notes);
        }

        return perfumeRepository.save(perfume);
    }

    public Perfume updatePerfume(Long id, Perfume updatedPerfume){
        Perfume perfume = getPerfumeById(id);
        perfume.setName(updatedPerfume.getName());
        perfume.setBrand(updatedPerfume.getBrand());
        perfume.setDescription(updatedPerfume.getDescription());
        perfume.setPrice(updatedPerfume.getPrice());
        perfume.setStock(updatedPerfume.getStock());
        perfume.setMl(updatedPerfume.getMl());

        // Gelen category id'si ile DB'den tam Category nesnesini çek
        if(updatedPerfume.getCategory() != null && updatedPerfume.getCategory().getId() != null){
            Category category = categoryRepository.findById(updatedPerfume.getCategory().getId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            perfume.setCategory(category);
        }

        // Gelen note id'leri ile DB'den tam Note nesnelerini çek
        if(updatedPerfume.getNotes() != null && !updatedPerfume.getNotes().isEmpty()){
            List<Note> notes = updatedPerfume.getNotes().stream()
                    .map(note -> noteRepository.findById(note.getId())
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
