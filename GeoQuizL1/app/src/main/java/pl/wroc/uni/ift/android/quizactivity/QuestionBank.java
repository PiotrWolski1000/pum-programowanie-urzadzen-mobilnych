package pl.wroc.uni.ift.android.quizactivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 15.11.2017.
 */

public class QuestionBank{
//    private static final QuestionBank ourInstance = new QuestionBank();

    private static QuestionBank ourInstance = null;


    private List<Question> mQuestionsBank;

    private QuestionBank() {
        mQuestionsBank = new ArrayList<>();
        mQuestionsBank.add(new Question(R.string.question_stolica_polski, true));
        mQuestionsBank.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        mQuestionsBank.add(new Question(R.string.question_sniezka, true));
        mQuestionsBank.add(new Question(R.string.question_wisla, true));
    }

    public static QuestionBank getInstance() {//singleton

        if(ourInstance == null){
            ourInstance = new QuestionBank();
        }
        return ourInstance;
    }

    public Question getQuestion(int index) {//zwraca wybrane pytanie
        return mQuestionsBank.get(index);
    }
    public List<Question> getQuestions(){
        return mQuestionsBank;
    }
    public int size(){//zwraca wielkosc listy(ilosc pytan)
        return mQuestionsBank.size();
    }
}
