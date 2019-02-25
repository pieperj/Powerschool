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


public class TeacherLoginFragment extends Fragment {


    EditText teacherEmailET, teacherPasswordET, teacherNameET;
    Button teacherLoginBTN, teacherSignUpBTN;
    TextView teacherCreateAccTV, teacherLoginTitleTV, teacherLoginErrorTV;

    public static final String TAG = "TeacherLoginFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_login, container, false);


        teacherEmailET = view.findViewById(R.id.ET_teacher_email);
        teacherPasswordET = view.findViewById(R.id.ET_teacher_password);
        teacherNameET = view.findViewById(R.id.ET_teacher_login_name);

        teacherLoginBTN = view.findViewById(R.id.BTN_teacher_log_in);
        teacherSignUpBTN = view.findViewById(R.id.BTN_teacher_sign_up);

        teacherCreateAccTV = view.findViewById(R.id.TV_teacher_create_account);
        teacherLoginTitleTV = view.findViewById(R.id.TV_teacher_login_title);
        teacherLoginErrorTV = view.findViewById(R.id.TV_teacher_login_error);

        teacherLoginErrorTV.setVisibility(View.GONE);

        teacherLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = teacherEmailET.getText().toString();
                String password = teacherPasswordET.getText().toString();

                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                        teacherLoginErrorTV.setVisibility(View.GONE);

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d(TAG, fault.toString());

                        teacherLoginErrorTV.setVisibility(View.VISIBLE);
                    }
                });

            }
        });


        teacherSignUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = teacherEmailET.getText().toString();
                String password = teacherPasswordET.getText().toString();
                String name = teacherNameET.getText().toString();

                if(!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && email.contains("@") ) {

                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("teacherName", name);

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(getActivity(), response.getEmail() + " was registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, fault.toString());
                        }
                    });

                    teacherEmailET.setText("");
                    teacherPasswordET.setText("");
                    teacherNameET.setText("");

                    setLoginView();

                }

            }
        });


        teacherCreateAccTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(teacherCreateAccTV.getText().toString().charAt(0) == 'D') {
                    Log.d(TAG, "switched to sign up");
                    setSignUpView();
                }

                else if(teacherCreateAccTV.getText().toString().charAt(0) == 'R') {
                    Log.d(TAG, "switched to login");
                    setLoginView();
                }

            }
        });


        return view;

    }

    public void setLoginView() {
        teacherNameET.setVisibility(View.GONE);
        teacherSignUpBTN.setVisibility(View.GONE);

        teacherLoginBTN.setVisibility(View.VISIBLE);
        teacherLoginTitleTV.setText("Teacher Log In");

        teacherCreateAccTV.setText("Don't have an account? Click here to sign up");
    }

    public void setSignUpView() {
        teacherNameET.setVisibility(View.VISIBLE);
        teacherSignUpBTN.setVisibility(View.VISIBLE);

        teacherLoginBTN.setVisibility(View.GONE);
        teacherLoginTitleTV.setText("Teacher Sign Up");

        teacherCreateAccTV.setText("Return to Login");
    }


}
