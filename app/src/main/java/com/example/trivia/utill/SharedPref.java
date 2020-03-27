package com.example.trivia.utill;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private SharedPreferences sharedPreferences;

    public SharedPref(Activity activity) {
        sharedPreferences=activity.getPreferences(activity.MODE_PRIVATE);
    }


    public void saveHighScore(int score){
        if(score>getHighScore()){
            sharedPreferences.edit().putInt("score", score).apply();
        }
    }
    public int getHighScore(){
        return sharedPreferences.getInt("score", 0);
    }
    public void setState(int index){
        sharedPreferences.edit().putInt("index", index).apply();
    }
    public int getState(){
        return sharedPreferences.getInt("index", 0);
    }
}
