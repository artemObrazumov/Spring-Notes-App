package com.artemObrazumov.notesapp.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserWithEmailExists extends RuntimeException {
    public UserWithEmailExists() {
        super("User with this email exists");
    }
}
