package com.artemObrazumov.notesapp.notes;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface NoteRepository extends ListCrudRepository<Note, Integer> {

    @Modifying
    @Query("UPDATE Note n SET n.title = :title WHERE n.id = :id")
    int updateTitle(@Param("title") String title, @Param("id") Long id);
}
