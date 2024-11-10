package com.artemObrazumov.notesapp.notes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime lastEditedOn;

    public Note(Integer id, String title, String content, LocalDateTime createdOn, LocalDateTime lastEditedOn, Integer version) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
        this.lastEditedOn = lastEditedOn;
        if (!lastEditedOn.isAfter(createdOn)) {
            throw new IllegalArgumentException("Last Editor On must be after Created On");
        }
    }

    public Note() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getLastEditedOn() {
        return lastEditedOn;
    }

    public void setLastEditedOn(LocalDateTime lastEditedOn) {
        this.lastEditedOn = lastEditedOn;
    }
}