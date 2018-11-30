package com.example.android.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.quizapp.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 3;

    private SQLiteDatabase db;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWERNUM + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable(){

        Question q1 = new Question("The location on a windows operating system where all the deleted files are stored is", "my computer", ">my documents", "the recycle bin", "the task bar", 3);
        addQuestion(q1);
        Question q2 = new Question("When you draw a plan of a house, you would make use of the following software", "database", "computer aided design", "computer graphics", "spreadsheet", 2);
        addQuestion(q2);
        Question q3 = new Question("In which of the following is batch processing used?", "booking a holiday", "checking stolen vehicles", "controlling of traffic lights", "producing a company\\'s payroll", 4);
        addQuestion(q3);
        Question q4 = new Question("Virtual memory is", "an extremely large main memory", "an extremely large secondary memory", "an illusion of extremely large main memory", "a type of memory used in super computers", 3);
        addQuestion(q4);
        Question q5 = new Question("Multiprogramming systems", "are easier to develop than single programming systems", "are used only on large main frame computers", "execute each job faster", "run several programs at the same time", 4);
        addQuestion(q5);
    }

    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWERNUM, question.getAnswerNum());
        db.insert(QuestionsTable.TABLE_NAME, null,cv);

    }

    public List<Question> getAllQuestion(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if(c.moveToFirst()) {
            do {
            Question question = new Question();
            question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
            question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
            question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
            question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
            question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
            question.setAnswerNum(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWERNUM)));

            questionList.add(question);

            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

}
