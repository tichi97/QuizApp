package com.example.android.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etId;
    private String name;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etId = findViewById(R.id.editTextId);


        Button buttonStartQuiz = findViewById(R.id.button_start);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = etId.getText().toString();
                name= etName.getText().toString();

                startQuiz();
            }
        });
    }

    private void startQuiz(){


        Intent intent = new Intent(MainActivity.this,QuizActivity.class);
        //Bundle b = new Bundle();
       // b.putString("id",id);
        //b.putString("name", name);
        intent.putExtra("name",name);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public String getName() {
        return name;
    }


    public String getId() {
        return id;
    }

}
