package com.example.pieperj.powerschool.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.example.pieperj.powerschool.R;
import com.example.pieperj.powerschool.fragments.StudentLoginFragment;
import com.example.pieperj.powerschool.fragments.TeacherLoginFragment;

public class LoginActivity extends AppCompatActivity {

    Button studentSignInBTN, teacherSignInBTN;
    TextView powerschoolTitleTV;
    ImageView powerschoolLogoIV;

    public static final String SECRET_KEY = "78E7D8B8-680F-4D09-FFC7-6AC17BEB5F00";
    public static final String APPLICATION_ID = "F0CA8104-2530-FAB7-FF28-6E99A247EB00";

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Backendless.initApp(this, APPLICATION_ID, SECRET_KEY);

        studentSignInBTN = findViewById(R.id.student_login);
        teacherSignInBTN = findViewById(R.id.teacher_login);
        powerschoolTitleTV = findViewById(R.id.powerschool_login);
        powerschoolLogoIV = findViewById(R.id.IV_powerschool_logo_home);

        studentSignInBTN.setVisibility(View.VISIBLE);
        teacherSignInBTN.setVisibility(View.VISIBLE);
        powerschoolTitleTV.setVisibility(View.VISIBLE);
        powerschoolLogoIV.setVisibility(View.VISIBLE);


        studentSignInBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                fragment = new StudentLoginFragment();

                studentSignInBTN.setVisibility(View.GONE);
                teacherSignInBTN.setVisibility(View.GONE);
                powerschoolTitleTV.setVisibility(View.GONE);
                powerschoolLogoIV.setVisibility(View.GONE);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.login_fragment_container, fragment);
                ft.commit();
            }
        });

        teacherSignInBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                fragment = new TeacherLoginFragment();

                studentSignInBTN.setVisibility(View.GONE);
                teacherSignInBTN.setVisibility(View.GONE);
                powerschoolTitleTV.setVisibility(View.GONE);
                powerschoolLogoIV.setVisibility(View.GONE);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.login_fragment_container, fragment);
                ft.commit();
            }
        });











    }


    /*
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
    */

}
