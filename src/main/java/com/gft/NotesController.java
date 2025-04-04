package com.gft;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
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

  @RequestMapping(
      value = "/edit/notes",
      method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Object> updateNote(@RequestBody Note note) {
    try {
      if (notesService.updateNote(note)) return Mono.just(note);
    } catch (BadRequestException bre) {
      return Mono.just(new ExceptionPojo(bre.getMessage(), 400));
    } catch (IOException ioe) {
      return Mono.just(new ExceptionPojo(ioe.getMessage(), 500));
    } catch (Exception e) {
      return Mono.just(new ExceptionPojo(e.getMessage(), 500));
    }

    return Mono.empty();
  }

  @ExceptionHandler({RuntimeException.class})
  public Mono<ExceptionPojo> handleException(Exception ex) {
    return Mono.just(new ExceptionPojo(ex.getMessage(), 500));
  }

  @ExceptionHandler({BadRequestException.class})
  public Mono<ExceptionPojo> handleException(BadRequestException ex) {
    return Mono.just(new ExceptionPojo(ex.getMessage(), 400));
  }
}
