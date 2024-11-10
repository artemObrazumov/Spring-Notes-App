package com.artemObrazumov.notesapp.notes;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    List<Note> findAll();
    Optional<Note> findById(Integer id);
    Integer create(Note note);
    void update(Note note, Integer id);
    void delete(Integer id);
}
