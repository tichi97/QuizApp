package com.example.android.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {
    private TextView txtId;
    private TextView txtName;
    private TextView txtResults;
    private TextView txtAttempted;
    private TextView txtFailed;
    private TextView txtNotAttempted;
    private TextView txtPhone;
    private Button btnHome;
    private int score,  questionCountTotal, attempted, failed, notAttempted;
    private String id, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        txtId = findViewById(R.id.textViewId);
        txtName = findViewById(R.id.textViewName);
        txtResults = findViewById(R.id.textViewResults);
        txtAttempted = findViewById(R.id.textViewAttempted);
        txtNotAttempted = findViewById(R.id.textViewNotAttempted);
        txtFailed = findViewById(R.id.textViewFailed);
        btnHome = findViewById(R.id.buttonHome);
        txtPhone = findViewById(R.id.textViewPhone);

        Bundle b =getIntent().getExtras();
        score = b.getInt("score");
        questionCountTotal = b.getInt("questionCounterTotal");
        attempted = b.getInt("attempted");
        notAttempted = b.getInt("notAttempted");
        failed = b.getInt("failed");
        name = b.getString("name");
        id= b.getString("id");


        txtId.setText("Your id: " + id);
        txtName.setText("Name: " + name);
        txtResults.setText("Your results = " + score + "/" + questionCountTotal);
        txtAttempted.setText("Questions Attempted = " + attempted);
        txtFailed.setText("Questions failed = " + failed);
        txtNotAttempted.setText("Questions not attempted = " + notAttempted);
        txtPhone.setText("Results sent to lecturer on 0798714595");

        sendSMS();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void sendSMS(){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+254716078736", null, "Results of " + name + " "+ id + " score: "+ score + "/" + questionCountTotal, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}


