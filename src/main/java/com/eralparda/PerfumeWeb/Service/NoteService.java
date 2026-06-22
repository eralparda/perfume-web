package com.eralparda.PerfumeWeb.Service;

import com.eralparda.PerfumeWeb.DTO.NoteRequest;
import com.eralparda.PerfumeWeb.Entity.Note;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
import com.eralparda.PerfumeWeb.Repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id){
        return noteRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Note not found with id! " + id));
    }

    public Note createNote(NoteRequest request){
        if(request.getId()!=null){
            Note note = getNoteById(request.getId());
            note.setName(request.getName());
            return noteRepository.save(note);
        }
        return noteRepository.save(Note.toModel(request));
    }

    public void deleteNote(Long id){
        noteRepository.deleteById(id);
    }
}
