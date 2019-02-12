package com.example.pieperj.powerschool;

import java.util.ArrayList;
import java.util.List;

public class LibraryAssignment {
    private static final LibraryAssignment ourInstance = new LibraryAssignment();

    private List<Assignment> library;

    public static LibraryAssignment getInstance() {
        return ourInstance;
    }

    private LibraryAssignment() {
        library = new ArrayList<>();
        library.add(new Assignment("Quiz 4.2", 35));
        library.add(new Assignment("Nonfiction Book Test", 60));
    }

    public List<Assignment> getLibrary() {
        return library;
    }
}
