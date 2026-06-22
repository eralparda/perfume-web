package com.eralparda.PerfumeWeb.Service;

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

    public Note createNote(Note note){
        return noteRepository.save(note);
    }

    public Note updateNote(Long id,Note updatedNote){
        Note note = getNoteById(id);
        note.setName(updatedNote.getName());
        return noteRepository.save(note);
    }

    public void deleteNote(Long id){
        noteRepository.deleteById(id);
    }
}
