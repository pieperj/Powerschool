package com.example.pieperj.powerschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    EditText emailET, passwordET, nameET;
    Button loginBTN, signUpBTN;
    TextView createAccTV, loginTitleTV;

    public static final String SECRET_KEY = "78E7D8B8-680F-4D09-FFC7-6AC17BEB5F00";
    public static final String APPLICATION_ID = "F0CA8104-2530-FAB7-FF28-6E99A247EB00";

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Backendless.initApp(this, APPLICATION_ID, SECRET_KEY);


        emailET = findViewById(R.id.ET_email);
        passwordET = findViewById(R.id.ET_password);
        nameET = findViewById(R.id.ET_login_name);

        loginBTN = findViewById(R.id.BTN_log_in);
        signUpBTN = findViewById(R.id.BTN_sign_up);

        createAccTV = findViewById(R.id.TV_create_account);
        loginTitleTV = findViewById(R.id.TV_login_title);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d(TAG, fault.toString());
                    }
                });

            }
        });

        signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String name = nameET.getText().toString();

                if(!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && email.contains("@") ) {

                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("name", name);

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(LoginActivity.this,
                                    response.getEmail() + " was registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, fault.toString());
                        }
                    });

                    emailET.setText("");
                    passwordET.setText("");
                    nameET.setText("");

                    setLoginView();

                }

            }
        });






        createAccTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(createAccTV.getText().toString().charAt(0) == 'D') {
                    Log.d(TAG, "switched to sign up");
                    setSignUpView();
                }

                else if(createAccTV.getText().toString().charAt(0) == 'R') {
                    Log.d(TAG, "switched to login");
                    setLoginView();
                }

            }
        });



    }



    public void setLoginView() {
        nameET.setVisibility(View.GONE);
        signUpBTN.setVisibility(View.GONE);

        loginBTN.setVisibility(View.VISIBLE);
        loginTitleTV.setText("Log In");

        createAccTV.setText("Don't have an account? Click here to sign up");
    }

    public void setSignUpView() {
        nameET.setVisibility(View.VISIBLE);
        signUpBTN.setVisibility(View.VISIBLE);

        loginBTN.setVisibility(View.GONE);
        loginTitleTV.setText("Sign Up");

        createAccTV.setText("Return to Login");
    }


}
