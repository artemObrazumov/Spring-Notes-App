package com.artemObrazumov.notesapp.notes.controller;

import com.artemObrazumov.notesapp.notes.dto.NoteDTO;
import com.artemObrazumov.notesapp.notes.entity.Note;
import com.artemObrazumov.notesapp.notes.exceptions.NoteNotFoundException;
import com.artemObrazumov.notesapp.notes.repository.NoteRepository;
import com.artemObrazumov.notesapp.users.entity.User;
import com.artemObrazumov.notesapp.users.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
@Tag(name = "Notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final JwtService jwtService;

    @GetMapping
    @Operation(summary = "Get all user notes")
    private List<Note> findAll() {
        Long userId = jwtService.getUserFromSecurityContextHolder().getId();
        return noteRepository.findAllByAuthorId(userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get note by id")
    private Note findById(@PathVariable Integer id) {
        Long userId = jwtService.getUserFromSecurityContextHolder().getId();
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            Note noteData = note.get();
            if (!noteData.getAuthor().equals(userId)) {
                throw new NoteNotFoundException();
            }
            return noteData;
        }
        throw new NoteNotFoundException();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create note")
    private Integer create(@Valid @RequestBody NoteDTO noteDTO) {
        User user = jwtService.getUserFromSecurityContextHolder();
        Note note = new Note(null, noteDTO.getTitle(), noteDTO.getContent(), user.getId(),
                LocalDateTime.now(), LocalDateTime.now());
        return noteRepository.save(note).getId();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    @Operation(summary = "Update note")
    private void update(@Valid @RequestBody NoteDTO noteDTO, @PathVariable Long id) {
        int linesAffected = noteRepository.updateTitle(noteDTO.getTitle(), id);
        if (linesAffected == 0) {
            throw new NoteNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete note")
    private void delete(@PathVariable Integer id) {
        Long userId = jwtService.getUserFromSecurityContextHolder().getId();
        noteRepository.deleteNoteOfAuthor(id, userId);
    }
}
