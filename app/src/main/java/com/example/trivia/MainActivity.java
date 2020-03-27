package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.model.Question;
import com.example.trivia.model.QuestionBank;
import com.example.trivia.utill.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextView;
    private TextView questionCounterTextView;
    private TextView scoreTv;
    private TextView highScoreTv;
    private Button trueButton;
    private Button falseButton;

    private ImageButton backButton;
    private ImageButton forwardButton;

    int currentQuestionIndex=0;
    List<Question> questionList;
    private int score=0;

    SharedPref pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setOnClick();
        pref=new SharedPref(this);
        //pref.saveHighScore(100);
        highScoreTv.setText("High Score: "+pref.getHighScore());
        currentQuestionIndex=pref.getState();
        Log.d("high: ", "onCreate: "+pref.getHighScore());
         questionList=new QuestionBank().getQuestionList(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d("inside", "processFinished: "+questionArrayList);
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                questionCounterTextView.setText(currentQuestionIndex+" out of "+questionArrayList.size());
            }
        });


        Log.d("size:", "onCreate: "+questionList.size());
        Log.d("cindex", "onCreate: "+currentQuestionIndex);
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
        scoreTv=findViewById(R.id.score_tv_id);
        highScoreTv=findViewById(R.id.highest_score_tv_id);

        trueButton=findViewById(R.id.true_button_id);
        falseButton=findViewById(R.id.false_button_id);

        backButton=findViewById(R.id.back_button_id);
        forwardButton=findViewById(R.id.next_button_id);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.next_button_id){
            //Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
            try{
                currentQuestionIndex=(currentQuestionIndex+1)%questionList.size();
                updateQuestion();
            }catch (Exception e){
                Toast.makeText(this, "Internet Connection Problem: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        if(id==R.id.back_button_id){
            //Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
            try {
                if(currentQuestionIndex>0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
            }catch (Exception e){
                Toast.makeText(this, "Internet Connection Problem: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        if(id==R.id.true_button_id){
            checkAnswer(true);
        }
        if(id==R.id.false_button_id){
          checkAnswer(false);
        }
    }

    private void checkAnswer(Boolean userAnswer){
        Boolean answer=false;
        try {
            answer=questionList.get(currentQuestionIndex).getAnswerTrue();
            if(userAnswer==answer){
                fadeAnimation();
                score+=10;
                scoreTv.setText("Your Score: "+score);
                Toast.makeText(this, "Correct!!",   Toast.LENGTH_SHORT).show();
            }else {
                shakeAnimation();
                if (score > 0) {
                    score -= 10;
                    scoreTv.setText("Your Score: " + score);
                    Toast.makeText(this, "Wrong!!", Toast.LENGTH_SHORT).show();
                }

            }
            }catch (Exception e){
            Toast.makeText(this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            currentQuestionIndex=(currentQuestionIndex+1)%questionList.size();
            updateQuestion();
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void updateQuestion() {
        questionTextView.setText(questionList.get(currentQuestionIndex).getAnswer());
        questionCounterTextView.setText(currentQuestionIndex+" out of "+questionList.size());
    }
    private void shakeAnimation(){
        Animation shakeAnimation= AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        final CardView cardView=findViewById(R.id.cardView_id);
        cardView.setAnimation(shakeAnimation);
        shakeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void fadeAnimation(){
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        final CardView cardView=findViewById(R.id.cardView_id);
        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        pref=new SharedPref(this);
        pref.saveHighScore(score);
        pref.setState(currentQuestionIndex);
        super.onPause();
    }
}
