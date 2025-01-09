package com.gft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class NotesController {
  @Autowired public NotesService notesService;

  @RequestMapping(
      value = "/notes",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<Note> getAllNotes() {
    return Flux.fromIterable(notesService.getAllNotes());
  }

  @RequestMapping(
      value = "/paginated/notes",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<Note> getPaginatedNotes(@RequestParam Integer start, @RequestParam Integer end) {
    return Flux.fromIterable(notesService.getPaginatedNotes(start, end));
  }

  @ExceptionHandler({RuntimeException.class})
  public Mono<ExceptionPojo> handleException(Exception ex) {
    return Mono.just(new ExceptionPojo(ex.getMessage(), 500));
  }
}
