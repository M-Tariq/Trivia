package com.example.trivia.data;

import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.Queue;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
