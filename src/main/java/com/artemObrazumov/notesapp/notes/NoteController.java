package com.artemObrazumov.notesapp.notes;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteRepository noteRepository;

    @Autowired
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
        return noteRepository.save(note).getId();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    private void update(@Valid @RequestBody Note note, @PathVariable Long id) {
        int linesAffected = noteRepository.updateTitle(note.getTitle(), id);
        if (linesAffected == 0) {
            throw new NoteNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id) {
        noteRepository.deleteById(id);
    }
}
