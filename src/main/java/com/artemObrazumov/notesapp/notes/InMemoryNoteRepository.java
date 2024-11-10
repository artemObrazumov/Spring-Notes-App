package com.artemObrazumov.notesapp.notes;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryNoteRepository implements NoteRepository {
    private List<Note> notes = new ArrayList<>();

    @Override
    public List<Note> findAll() {
        return notes;
    }

    @Override
    public Optional<Note> findById(Integer id) {
        return notes.stream()
                .filter(note -> Objects.equals(note.id(), id))
                .findFirst();
    }

    @Override
    public Integer create(Note note) {
        notes.add(note);
        return note.id();
    }

    @Override
    public void update(Note updatedNote, Integer id) {
        Optional<Note> existingNote = findById(id);
        existingNote.ifPresent(note -> notes.set(notes.indexOf(note), updatedNote));
    }

    @Override
    public void delete(Integer id) {
        notes.removeIf(note -> Objects.equals(note.id(), id));
    }
}
