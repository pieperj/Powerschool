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

    }

    public List<Assignment> getLibrary() {
        return library;
    }
}
