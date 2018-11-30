package com.example.android.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionNum;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button btnNext;
    private Button btnPrev;
    private Button btnHome;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private Question prevQuestion;

    private int score;
    private boolean answered ;
    private int attempted;
    private int failed;
    private int notAttempted;
    private String name;
    private String id;


    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.textView_question);
        textViewScore = findViewById(R.id.textView_score);
        textViewQuestionNum = findViewById(R.id.textView_question_num);
        rbGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        btnNext = findViewById(R.id.button_next);
        btnPrev = findViewById(R.id.button_prev);
        btnHome = findViewById(R.id.buttonHome);

       // Bundle b = new Bundle();
        //id = b.getString("id");
        //name = b.getString("name");

        Intent in = getIntent();
        name = in.getExtras().getString("name");
        id = in.getExtras().getString("id");

        QuizDBHelper dbHelper = new QuizDBHelper(this);
        questionList = dbHelper.getAllQuestion();
        questionCountTotal=(questionList.size());
        Collections.shuffle(questionList);

        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (!answered){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer();
                    }else{
                        Toast.makeText(QuizActivity.this,"Please select an answer",Toast.LENGTH_LONG);
                    }
                }else{
                    showNextQuestion();
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPreviousQuestion();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showNextQuestion(){
            rbGroup.clearCheck();



        if(questionCounter< getQuestionCountTotal()){
           /* if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                questionCounter++;
            }*/

            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionNum.setText("Question: " + questionCounter + "/" + getQuestionCountTotal());
            answered = false;

            btnNext.setText("Next");//it was confirm first

        }else{
            btnNext.setText("Submit");
            showResults();
        }
    }

    private void showPreviousQuestion(){
        questionCounter=questionCounter-1;
        if(questionCounter<1){

        }

        currentQuestion = questionList.get(questionCounter);

        textViewQuestion.setText(currentQuestion.getQuestion());
        rb1.setText(currentQuestion.getOption1());
        rb2.setText(currentQuestion.getOption2());
        rb3.setText(currentQuestion.getOption3());
        rb4.setText(currentQuestion.getOption4());

        textViewQuestionNum.setText("Question: " + questionCounter + "/" + getQuestionCountTotal());

    }

    private void checkAnswer(){
        answered=true;

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNum = rbGroup.indexOfChild(rbSelected) + 1;

        if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
            attempted++;
        }else{
            notAttempted++;
        }

        if(answerNum == currentQuestion.getAnswerNum()){
            score++;
        }else{
            failed++;
        }

        if(questionCounter< getQuestionCountTotal()){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");
        }
    }

    private void showResults(){

        Intent intent = new Intent(QuizActivity.this,ResultsActivity.class);
        Bundle b = new Bundle();
        b.putInt("score", score);
        b.putInt("questionCounterTotal", questionCountTotal);
        b.putInt("attempted",attempted);
        b.putInt("notAttempted",notAttempted);
        b.putInt("failed",failed);
        b.putString("id",id);
        b.putString("name",name);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void finishQuiz(){
        finish();
    }


    public int getQuestionCountTotal() {
        return questionCountTotal;
    }

    public int getScore() {
        return score;
    }

    public int getAttempted() {
        return attempted;
    }

    public int getFailed() {
        return failed;
    }

    public int getNotAttempted() {
        return notAttempted;
    }

}
