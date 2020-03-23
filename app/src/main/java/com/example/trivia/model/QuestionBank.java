package com.example.trivia.model;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.data.AnswerListAsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    private String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    ArrayList<Question> questionList=new ArrayList<>();

    public List<Question> getQuestionList(final AnswerListAsyncResponse callBack) {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, (JSONArray) null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("JSON", "" + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Question question = new Question();
                        question.setAnswer(response.getJSONArray(i).get(0).toString());
                        question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
                        questionList.add(question);

                        //Log.d("question:", "" + response.getJSONArray(0).get(0));
                        //Log.d("answer:", "" + response.getJSONArray(0).get(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }if (callBack!=null){
                    callBack.processFinished(questionList);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", ""+error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionList;
    }


}
