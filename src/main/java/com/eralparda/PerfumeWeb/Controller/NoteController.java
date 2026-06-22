package com.eralparda.PerfumeWeb.Controller;

import com.eralparda.PerfumeWeb.DTO.NoteRequest;
import com.eralparda.PerfumeWeb.Entity.Note;
import com.eralparda.PerfumeWeb.Service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public List<Note> getAllNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }

    @PostMapping
    public Note createNote(@RequestBody NoteRequest request){
        return noteService.createNote(request);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id){
        noteService.deleteNote(id);
    }
}
