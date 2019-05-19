package com.example.pieperj.powerschool;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

public class EditRosterFragment extends Fragment {

    private List<Student> roster;

    private EditText submitStudentET, removeStudentET;
    private Button submitBTN, removeBTN;
    private CheckBox check9, check10, check11, check12;
    private TextView addMessageTV, removeMessageTV;
    private Student currentStudent;
    private ArrayAdapter<Student> adapter;

    public static final String TAG = "EditRosterFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_roster, container, false);

        submitBTN = view.findViewById(R.id.BTN_submit);
        removeBTN = view.findViewById(R.id.BTN_remove);
        submitStudentET = view.findViewById(R.id.ET_submit_student);
        removeStudentET = view.findViewById(R.id.ET_remove_student);
        check9 = view.findViewById(R.id.CB_9);
        check10 = view.findViewById(R.id.CB_10);
        check11 = view.findViewById(R.id.CB_11);
        check12 = view.findViewById(R.id.CB_12);
        addMessageTV = view.findViewById(R.id.TV_add_message);
        removeMessageTV = view.findViewById(R.id.TV_remove_message);

        addMessageTV.setText("");
        removeMessageTV.setText("");

        roster = new ArrayList<>();

        adapter = new ArrayAdapter(EditRosterFragment.this.getActivity(), R.layout.attendance_custom_list_item, roster);


        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = submitStudentET.getText().toString();

                int year = 9;
                if(check9.isChecked()) {
                    year = 9;
                }

                else if(check10.isChecked()) {
                    year = 10;
                }

                else if(check11.isChecked()) {
                    year = 11;
                }
                else if(check12.isChecked()) {
                    year = 12;
                }

                if(currentStudent == null) {
                    currentStudent = new Student(name, year);
                    roster.add(currentStudent);
                }

                else {
                    currentStudent.setName(name);
                }
                adapter.notifyDataSetChanged();

                currentStudent = new Student(name, year);
                Library.getInstance().getStudents().add(currentStudent);


                Backendless.Persistence.of(Student.class).save(currentStudent, new AsyncCallback<Student>() {
                    @Override
                    public void handleResponse(Student response) {
                        Toast.makeText(getActivity(), response.getName() + " has been added", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, response.getName() + " was saved.");
                        currentStudent = null;

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d(TAG, fault.toString());
                    }
                });

                submitStudentET.setText("");
                check9.setChecked(false);
                check10.setChecked(false);
                check11.setChecked(false);
                check12.setChecked(false);

                addMessageTV.setText("" + currentStudent.getName() + " was added");
            }
        });

        removeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = removeStudentET.getText().toString();



                for(int i = 0; i < Library.getInstance().getStudents().size(); i++) {


                    if(Library.getInstance().getStudents().get(i).getName().equals(name)) {
                        currentStudent = Library.getInstance().getStudents().get(i);
                        currentStudent.setObjectId(Backendless.Persistence.of(Student.class).find().get(i).getObjectId());
                        //Log.d(TAG, currentStudent.getName());
                        break;
                    }

                }


                Backendless.Persistence.of(Student.class).remove(currentStudent, new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {
                        Toast.makeText(getActivity(), currentStudent.getName() + " was removed", Toast.LENGTH_SHORT).show();

                        removeStudentET.setText("");
                        addMessageTV.setText("" + currentStudent.getName() + "\nwas removed");
                        removeMessageTV.setText("" + currentStudent.getName() + " was removed");
                        //currentStudent = null;
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d("TAG", fault.toString());
                    }
                });


            }
        });


        return view;
    }
}
