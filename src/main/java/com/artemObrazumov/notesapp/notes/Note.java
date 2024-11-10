package com.artemObrazumov.notesapp.notes;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record Note(
        Integer id,
        @NotEmpty
        String title,
        @NotEmpty
        String content,
        LocalDateTime createdOn,
        LocalDateTime lastEditedOn
) {

    public Note {
        if (!lastEditedOn.isAfter(createdOn)) {
            throw new IllegalArgumentException("Last Editor On must be after Created On");
        }
    }
}