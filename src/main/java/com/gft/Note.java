package com.gft;

import java.util.List;

public class Note {
    private String title;
    private List<String> contents;

    public Note(String title, List<String> contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getContents() {
        return this.contents;
    }
}
