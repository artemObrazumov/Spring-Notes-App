package com.artemObrazumov.notesapp.notes;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping
    private List<Note> findAll() {
        return noteRepository.findAll();
    }

    @GetMapping("/{id}")
    private Note findById(@PathVariable Integer id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return note.get();
        }
        throw new NoteNotFoundException();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    private Integer create(@Valid @RequestBody Note note) {
        return noteRepository.create(note);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    private void update(@Valid @RequestBody Note note, @PathVariable Integer id) {
        noteRepository.update(note, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id) {
        noteRepository.delete(id);
    }
}
