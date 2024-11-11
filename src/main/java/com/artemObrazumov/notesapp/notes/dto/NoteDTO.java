package com.artemObrazumov.notesapp.notes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(description = "Note DTO")
public class NoteDTO {

    @Schema(description = "Title", example = "Note title")
    @Size(min = 3, max = 200, message = "Note title length should be in range 3..200")
    @NotBlank(message = "Title cant be blank")
    private String title;

    @Schema(description = "Content", example = "Note content")
    @Size(max = 500, message = "Note content length should be lower or equal 500")
    private String content;
}