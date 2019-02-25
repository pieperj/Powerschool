package com.example.pieperj.powerschool;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class StudentLoginFragment extends Fragment {

    EditText studentEmailET, studentPasswordET, studentNameET;
    Button studentLoginBTN, studentSignUpBTN;
    TextView studentCreateAccTV, studentLoginTitleTV, studentLoginErrorTV;

    public static final String TAG = "StudentLoginFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_login, container, false);

        studentEmailET = view.findViewById(R.id.ET_student_email);
        studentPasswordET = view.findViewById(R.id.ET_student_password);
        studentNameET = view.findViewById(R.id.ET_student_login_name);

        studentLoginBTN = view.findViewById(R.id.BTN_student_log_in);
        studentSignUpBTN = view.findViewById(R.id.BTN_student_sign_up);

        studentCreateAccTV = view.findViewById(R.id.TV_student_create_account);
        studentLoginTitleTV = view.findViewById(R.id.TV_student_login_title);
        studentLoginErrorTV = view.findViewById(R.id.TV_student_login_error);

        studentLoginErrorTV.setVisibility(View.GONE);

        studentLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = studentEmailET.getText().toString();
                String password = studentPasswordET.getText().toString();

                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                        studentLoginErrorTV.setVisibility(View.GONE);

                        Intent intent = new Intent(getActivity(), StudentActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d(TAG, fault.toString());

                        studentLoginErrorTV.setVisibility(View.VISIBLE);

                    }
                });

            }
        });





        studentSignUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = studentEmailET.getText().toString();
                String password = studentPasswordET.getText().toString();
                String name = studentNameET.getText().toString();

                if(!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && email.contains("@") ) {

                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("studentName", name);

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(getActivity(), response.getEmail() + " was registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), StudentActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, fault.toString());
                        }
                    });

                    studentEmailET.setText("");
                    studentPasswordET.setText("");
                    studentNameET.setText("");

                    setLoginView();

                }

            }
        });

        studentCreateAccTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(studentCreateAccTV.getText().toString().charAt(0) == 'D') {
                    Log.d(TAG, "switched to sign up");
                    setSignUpView();
                }

                else if(studentCreateAccTV.getText().toString().charAt(0) == 'R') {
                    Log.d(TAG, "switched to login");
                    setLoginView();
                }

            }
        });






        return view;
    }


    public void setLoginView() {
        studentNameET.setVisibility(View.GONE);
        studentSignUpBTN.setVisibility(View.GONE);

        studentLoginBTN.setVisibility(View.VISIBLE);
        studentLoginTitleTV.setText("Student Log In");

        studentCreateAccTV.setText("Don't have an account? Click here to sign up");
    }

    public void setSignUpView() {
        studentNameET.setVisibility(View.VISIBLE);
        studentSignUpBTN.setVisibility(View.VISIBLE);

        studentLoginBTN.setVisibility(View.GONE);
        studentLoginTitleTV.setText("Student Sign Up");

        studentCreateAccTV.setText("Return to Login");
    }


}
