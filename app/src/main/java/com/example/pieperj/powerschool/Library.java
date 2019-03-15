package com.example.pieperj.powerschool;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static final Library ourInstance = new Library();

    private List<Student> students;
    private int totalDays;

    public static Library getInstance() {
        return ourInstance;
    }

    private Library() {
        totalDays = 10;
        students = new ArrayList<>();

        DataQueryBuilder query = DataQueryBuilder.create();

        Backendless.Persistence.of(Student.class).find(query, new AsyncCallback<List<Student>>() {
            @Override
            public void handleResponse(List<Student> response) {
                Log.d("Library", "" + response);
                students.clear();
                students.addAll(response);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("MainActivity", fault.toString());
            }
        });
        /*
        students.add(new Student("James Pieper", 11));
        students.add(new Student("Dave Buster", 9));
        students.add(new Student("Mack Donald", 12));
        students.add(new Student("Nick Smith", 10));
        */
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public void addTotalDays() {
        this.totalDays++;
    }

    public List<Student> getStudents() {

        return students;
    }
}
