package pl.wroc.uni.ift.android.quizactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    // Key for questions array to be stored in bundle;
    private static final String KEY_QUESTIONS = "questions";

    private static final int CHEAT_REQEST_CODE = 0;


    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;

    private Button mCheatButton;

//    private Question[] mQuestionsBank = new Question[]{
//            new Question(R.string.question_stolica_polski, true),
//            new Question(R.string.question_stolica_dolnego_slaska, false),
//            new Question(R.string.question_sniezka, true),
//            new Question(R.string.question_wisla, true)
//    };

    private QuestionBank mQuestionsBank2;

    private int mCheatCounter = 3;
    private int mCurrentIndex = 0;
    private int mCorrectCount = 0;
    private int mAnswered = 0;




    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");

        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);

        mCheatButton = (Button) findViewById(R.id.button_cheat);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean currentAnswer = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                boolean IsCheated =  mQuestionsBank[mCurrentIndex].getWasCheated();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, currentAnswer, IsCheated);

                startActivityForResult(intent, CHEAT_REQEST_CODE);
            }
        });


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nextQuestion();
//                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                    }
                }
        );

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
                updateQuestion();

            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == 0)
                    mCurrentIndex = 0;
                else
                    mCurrentIndex = (mCurrentIndex - 1);
                updateQuestion();
            }
        });

        checkCheatCounter();
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CHEAT_REQEST_CODE) {
            if (data != null)
            {
                boolean answerWasShown = CheatActivity.wasAnswerShown(data);
                if (answerWasShown) {
                    Toast.makeText(this,
                            R.string.message_for_cheaters,
                            Toast.LENGTH_LONG)
                            .show();
                    mQuestionsBank[mCurrentIndex].setWasCheated(true);
                    mCheatCounter--;
                    checkCheatCounter();
                }
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, String.format("onSaveInstanceState: current index %d ", mCurrentIndex) );

        //we still have to store current index to correctly reconstruct state of our app
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

        // because Question is implementing Parcelable interface
        // we are able to store array in Bundle
        savedInstanceState.putParcelableArray(KEY_QUESTIONS, mQuestionsBank);
        savedInstanceState.putInt("CheatsCounter", mCheatCounter);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mQuestionsBank=(Question []) savedInstanceState.getParcelableArray(KEY_QUESTIONS);
        mCheatCounter = savedInstanceState.getInt("CheatsCounter");
    }

    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void nextQuestion(){
        if(mCurrentIndex == mQuestionsBank.length-1){
            mCurrentIndex = mQuestionsBank.length-1;
        }
        else{
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
        }
    }
    private void checkCheatCounter(){
        if (mCheatCounter <= 0) {
            mCheatButton.setVisibility(View.INVISIBLE);
        }
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        Question question = mQuestionsBank[mCurrentIndex];
        boolean answerIsTrue = question.isAnswerTrue();

        int toastMessageId = 0;
        if (!question.checkIsAnswered() && mAnswered!=mQuestionsBank.length)
        {
            nextQuestion();
            updateQuestion();
            if (userPressedTrue == answerIsTrue) {

                toastMessageId = R.string.correct_toast;
                mCorrectCount++;
            } else {
                toastMessageId = R.string.incorrect_toast;
            }
            question.setAnswered(true);
            mAnswered++;
        }
        else
        {
           toastMessageId = R.string.already_answered;

        }

        Toast toast = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,175);
        toast.show();

        if (mAnswered==mQuestionsBank.length)
        {
            toastMessageId = R.string.final_notification;
            String message = getString(toastMessageId)+" "+mCorrectCount+" / "+mQuestionsBank.length;
            Toast toast1 = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.BOTTOM,0,0);
            toast1.show();
        }
    }
}
