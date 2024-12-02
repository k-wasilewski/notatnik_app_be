package com.gft;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class NotesController {
    @Autowired
    public NotesService notesService;

    @GetMapping("/notes")
    public List<List<String>> getAllNotes() {
        return notesService.getAllNotes();
    }

    @ExceptionHandler({ RuntimeException.class })
    public ExceptionPojo handleException(Exception ex) {
        return new ExceptionPojo(ex.getMessage(), 500);
    }
}
