package com.eralparda.PerfumeWeb.Entity;

import com.eralparda.PerfumeWeb.DTO.NoteRequest;
import  jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public static Note toModel(NoteRequest request){
        Note note = new Note();
        note.setName(request.getName());
        return note;
    }
}
