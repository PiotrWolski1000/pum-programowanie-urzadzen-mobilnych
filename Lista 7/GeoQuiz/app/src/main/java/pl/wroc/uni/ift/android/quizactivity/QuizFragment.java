package pl.wroc.uni.ift.android.quizactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import static android.app.Activity.RESULT_OK;

public class QuizFragment extends Fragment {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_TOKENS = "TokensCount";
    // Key for questions array to be stored in bundle;
    private static final String KEY_QUESTIONS = "questions";
    private static final String QUESTION_ID = "question_id";

    private static final int CHEAT_REQEST_CODE = 0;

    private int mCheatTokens = 3;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;
    private TextView mAPITextView;

    private Button mCheatButton;
    private Button mQuestionListButton;

    private int mQuestionId;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    private int QUESTIONS_COUNT = getQuestionsBankLength();
    private int correct_answers = getCorrectAnsweredCount();
    private int tokens_count = getTokensCount();
    private int mCurrentIndex = 0;

    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application


    public static QuizFragment newInstance(int id)
    {
        Bundle args = new Bundle();
        args.putInt(QUESTION_ID, id);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
//            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mCheatTokens = savedInstanceState.getInt(KEY_TOKENS);
            Log.i(TAG, String.format("onCreate(): Restoring saved index: %d", mCurrentIndex));
            mQuestionsBank = (Question []) savedInstanceState.getParcelableArray(KEY_QUESTIONS);
            if (mQuestionsBank == null)
            {
                Log.e(TAG, "Question bank array was not correctly returned from Bundle");

            } else {
                Log.i(TAG, "Question bank array was correctly returned from Bundle");
            }
        }
        mQuestionId = getArguments().getInt(QUESTION_ID);
        mCurrentIndex = getArguments().getInt(KEY_INDEX);

    }

    // @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_quiz, container, false);
        Log.d(TAG, "onCreate() called");

        //setTitle(R.string.app_name);
        // inflating view objects
        //setContentView(R.layout.activity_quiz);

        mCheatButton = (Button) view.findViewById(R.id.button_cheat);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);

                boolean currentAnswer = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                boolean IsCheated =  mQuestionsBank[mCurrentIndex].getWasCheated();
                Intent intent = CheatActivity.newIntent(getActivity(), currentAnswer, IsCheated, mCheatTokens);
//
//                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
//                boolean currentAnswer = mQuestionsBank[mCurrentIndex].isAnswerTrue();
//                intent.putExtra("answer", currentAnswer);

                startActivityForResult(intent, CHEAT_REQEST_CODE);
            }
        });

        mQuestionListButton = (Button) view.findViewById(R.id.button_question_list);
        mQuestionListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),QuestionListActivity.class);
                startActivity(intent);
            }
        });

        // set API Level in the textview
        mAPITextView = (TextView) view.findViewById(R.id.textView_API);
        mAPITextView.setText("API Level: "+Integer.valueOf(android.os.Build.VERSION.SDK_INT).toString());

        mQuestionTextView = (TextView) view.findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) view.findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                        mQuestionsBank[mCurrentIndex].setIsAnswered(true);
                        if(areAllQuestionsAnswered()==true)
                        {
                            //start activity
                            Intent intent = new Intent(getActivity(),SummaryActivity.class);
                            startActivity(intent);
                        }

                    }
                }
        );

        mFalseButton = (Button) view.findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

                mQuestionsBank[mCurrentIndex].setIsAnswered(true);

                if(areAllQuestionsAnswered()==true)
                {
                    //start activity
                    Intent intent = new Intent(getActivity(),SummaryActivity.class);

//                    intent.putExtra("ilosc", QUESTIONS_COUNT);
//                    intent.putExtra("poprawne", correct_answers);
//                    intent.putExtra("tokens", tokens_count);


                    startActivity(intent);
                }
            }
        });

        mNextButton = (ImageButton) view.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) view.findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == 0)
                    mCurrentIndex = mQuestionsBank.length - 1;
                else
                    mCurrentIndex = (mCurrentIndex - 1);
                updateQuestion();
            }
        });

        updateQuestion();
        return view;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CHEAT_REQEST_CODE) {
            if (data != null)
            {
                boolean answerWasShown = CheatActivity.wasAnswerShown(data);
                if (answerWasShown) {

                    Toast.makeText(getActivity(),
                            R.string.message_for_cheaters,
                            Toast.LENGTH_LONG)
                            .show();
                    mQuestionsBank[mCurrentIndex].setWasCheated(true);
                }

                mCheatTokens = CheatActivity.getCheatTokens(data);
                Log.d("Tokens result",Integer.toString(mCheatTokens));

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
        savedInstanceState.putInt(KEY_TOKENS,mCheatTokens);
    }

//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState)
//    {
//        super.onRestoreInstanceState(savedInstanceState);
//        mQuestionsBank=(Question []) savedInstanceState.getParcelableArray(KEY_QUESTIONS);
//        mCheatTokens = savedInstanceState.getInt(KEY_TOKENS);
//    }

    public int getQuestionsBankLength(){
        return mQuestionsBank.length;
    }
    private void updateQuestion() {
//        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        int question = mQuestionId;

        mQuestionTextView.setText(question);
        //we have to remember abobut updating mJCurrentIndex of our question
        for (int i =0 ; i < mQuestionsBank.length; i++){
            if (mQuestionsBank[i].getTextResId() == question) mCurrentIndex = i;
        }

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int toastMessageId;

        if (userPressedTrue == answerIsTrue) {
            toastMessageId = R.string.correct_toast;

        } else {
            toastMessageId = R.string.incorrect_toast;
        }


        Toast.makeText(getContext(), toastMessageId, Toast.LENGTH_SHORT).show();//after change there was getActivity() instead of getContext
    }

    //function below checks if all of our question in our quiz- game is answered, so we can start acitivity which shows
    //us result of our game e.g; how many points did we get, how many life savers do we still have(if any) etc.
    public Boolean areAllQuestionsAnswered(){
        for(int i = 0;i<= mQuestionsBank.length-1;i++){
            if(mQuestionsBank[i].getIsAnswered() == false) {
                return false;//return false when there is at least one not answered question
            }else
                if(i == mQuestionsBank.length-1)
                    return true;//return true when all questions was already answered
            }
        return null;
    }

    public int getCorrectAnsweredCount(){
        int ilosc = 0;
        for(int i = 0;i <= mQuestionsBank.length-1;i++){
            if(mQuestionsBank[i].isAnswerTrue() == true)
            ilosc += 1;
        }
        return ilosc;
    }

    public int getTokensCount() {
        int ilosc = 0;
        for(int i = 0;i <= mQuestionsBank.length-1;i++){
            if(mQuestionsBank[i].getWasCheated() == true)
                ilosc += 1;
        }
        return ilosc;
    }
}
