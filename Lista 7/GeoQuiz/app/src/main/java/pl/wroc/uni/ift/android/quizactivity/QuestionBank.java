package pl.wroc.uni.ift.android.quizactivity;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by BeaR on 20.11.2017.
 */

public class QuestionBank {

    private static QuestionBank instance = null;


    public static QuestionBank getInstance()
    {
        if(instance == null)
            instance = new QuestionBank();
        return instance;
    }

    private List<Question> mQuestionsBank = new ArrayList<>();
    private QuestionBank()
    {
        mQuestionsBank.add(new Question(R.string.question_stolica_polski, true ));
        mQuestionsBank.add(new Question(R.string.question_stolica_dolnego_slaska, false ));
        mQuestionsBank.add(new Question(R.string.question_sniezka, true ));
        mQuestionsBank.add(new Question(R.string.question_wisla, true ));

    }

    //zwraca jedno pytanie (instancję klasy)
    public Question getQuestion(int index)
    {
        return mQuestionsBank.get(index);
    }

    // zwraca listę wszystkich pytań
    public List<Question> getQuestions()
    {
        return mQuestionsBank;
    }

    // zwraca ilość pytań
    public int mySize()
    {
        return mQuestionsBank.size();
    }

    public void addQuestion(Question q)
    {
        mQuestionsBank.add(q);
    }

}

