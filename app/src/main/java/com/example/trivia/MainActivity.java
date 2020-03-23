package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.model.Question;
import com.example.trivia.model.QuestionBank;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextView;
    private TextView questionCounterTextView;

    private Button trueButton;
    private Button falseButton;

    private ImageButton backButton;
    private ImageButton forwardButton;

    int currentQuestionIndex=0;
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setOnClick();

         questionList=new QuestionBank().getQuestionList(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d("inside", "processFinished: "+questionArrayList);
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
            }
        });

        questionCounterTextView.setText(currentQuestionIndex+" out of "+questionList.size());

//         questionTextView.setText(questionList.get(0).getAnswer());
}

    private void setOnClick() {
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        forwardButton.setOnClickListener(this);
    }

    private void initView() {
        questionTextView=findViewById(R.id.question_tv);
        questionCounterTextView=findViewById(R.id.question_counter_tv_id);

        trueButton=findViewById(R.id.true_button_id);
        falseButton=findViewById(R.id.false_button_id);

        backButton=findViewById(R.id.back_button_id);
        forwardButton=findViewById(R.id.next_button_id);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.next_button_id){
            Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
            currentQuestionIndex=(currentQuestionIndex+1)%questionList.size();
            updateQuestion();
        }
        if(id==R.id.back_button_id){
            Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
            if(currentQuestionIndex>0) {
                currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                updateQuestion();
            }
        }
        if(id==R.id.true_button_id){
            //Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
            if(questionList.get(currentQuestionIndex).getAnswerTrue()){
                Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "False", Toast.LENGTH_SHORT).show();
            }

        }
        if(id==R.id.false_button_id){
            //Toast.makeText(this, "False", Toast.LENGTH_SHORT).show();
            if(!questionList.get(currentQuestionIndex).getAnswerTrue()){
                Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "False", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateQuestion() {
        questionTextView.setText(questionList.get(currentQuestionIndex).getAnswer());
        questionCounterTextView.setText(currentQuestionIndex+" out of "+questionList.size());
    }
}
