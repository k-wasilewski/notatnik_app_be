package com.gft;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class NotesController {
    @Autowired
    public NotesService notesService;

    @RequestMapping(value = "/notes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Note> getAllNotes() {
        System.out.println(notesService.getAllNotes());
        return notesService.getAllNotes();
    }

    @ExceptionHandler({ RuntimeException.class })
    public ExceptionPojo handleException(Exception ex) {
        System.out.println(ex.getMessage());
        return new ExceptionPojo(ex.getMessage(), 500);
    }
}
