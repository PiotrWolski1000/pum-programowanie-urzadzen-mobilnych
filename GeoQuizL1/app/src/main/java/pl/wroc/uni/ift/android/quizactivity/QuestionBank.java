package pl.wroc.uni.ift.android.quizactivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 15.11.2017.
 */

public class QuestionBank {
    private static final QuestionBank ourInstance = new QuestionBank();

    List<String> mQuestionList = new ArrayList<String>();

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    public static QuestionBank getInstance(int index) {

        return ourInstance;
    }

    private QuestionBank() {
        System.out.println("elo!");
    }
    public int Size(){
        return mQuestionList.size();
    }
}
