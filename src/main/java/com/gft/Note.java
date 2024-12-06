package com.gft;

import java.util.List;

public class Note {
    private String title;
    private List<String> contents;

    public Note() {}

    public Note(String title, List<String> contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public List<String> getContents() {
        return this.contents;
    }

    public String toString() {
        return title+contents;
    }
}
