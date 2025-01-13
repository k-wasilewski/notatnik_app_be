package com.gft;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService {
  @Autowired public ResourceUtils resourceUtils;
  private final String NOTES_PATH = "/notes";

  public List<Note> getAllNotes() {
    List<String> filenames = resourceUtils.getResourceFilenames();

    return filenames.stream()
        .map(
            filename -> {
              try {
                return new Note(
                    filename, resourceUtils.getResourceContentsByPath(NOTES_PATH + "/" + filename));
              } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
              }
            })
        .collect(Collectors.toList());
  }

  public List<Note> getPaginatedNotes(Integer start, Integer end) {
    List<String> filenames = resourceUtils.getResourceFilenames();

    return filenames.stream()
        .skip(start)
        .limit(end)
        .map(
            filename -> {
              try {
                return new Note(
                    filename, resourceUtils.getResourceContentsByPath(NOTES_PATH + "/" + filename));
              } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
              }
            })
        .collect(Collectors.toList());
  }

  public void updateNote(Note note) throws BadRequestException, IOException, Exception {
    List<String> filenames = resourceUtils.getResourceFilenames();
    if (!filenames.contains(note.getTitle())) throw new BadRequestException("Note does not exist");

    resourceUtils.writeContentsToResource(note.getTitle(), note.getContents(), NOTES_PATH + "/" + note.getTitle() + ".md");
  }
}
