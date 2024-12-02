package com.gft;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService {
    @Autowired
    public ResourceUtils resourceUtils;
    
    public List<List<String>> getAllNotes() {
        final String NOTES_PATH = "/notes";

        List<String> filenames = resourceUtils.getResourceFilenames();

        return filenames.stream().map(filename -> {
            try {
                return resourceUtils.getResourceContentsByPath(NOTES_PATH + "/" + filename);
            } catch(IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }).collect(Collectors.toList());
    }
}
