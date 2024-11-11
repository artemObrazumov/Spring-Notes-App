package com.artemObrazumov.notesapp.notes.repository;

import com.artemObrazumov.notesapp.notes.entity.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Transactional
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("SELECT n FROM Note n WHERE n.author = :authorId")
    List<Note> findAllByAuthorId(@Param("authorId") Long authorId);

    @Modifying
    @Query("UPDATE Note n SET n.title = :title WHERE n.id = :id")
    int updateTitle(@Param("title") String title, @Param("id") Long id);

    @Query("DELETE Note n WHERE n.id = :noteId AND n.author = :authorId")
    void deleteNoteOfAuthor(@Param("noteId") Integer noteId, @Param("authorId") Long authorId);
}
