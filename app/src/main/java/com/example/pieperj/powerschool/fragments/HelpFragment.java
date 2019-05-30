package com.example.pieperj.powerschool.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pieperj.powerschool.R;


public class HelpFragment extends Fragment {

    private EditText recipientsET, subjectET, messageET;
    private Button sendBTN;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        recipientsET = view.findViewById(R.id.ET_email_recipients);
        subjectET = view.findViewById(R.id.ET_email_subject);
        messageET = view.findViewById(R.id.ET_email_message);

        sendBTN = view.findViewById(R.id.BTN_email_send);


        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] addresses = recipientsET.getText().toString().split(",");
                String subject = subjectET.getText().toString();
                String message = messageET.getText().toString();

                composeEmail(addresses, subject, message);
            }
        });


        return view;
    }

    public void composeEmail(String[] addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }

    }


}
