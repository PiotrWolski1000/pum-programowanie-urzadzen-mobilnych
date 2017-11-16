package pl.wroc.uni.ift.android.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private final static String EXTRA_KEY_ANSWER = "Answer";
    private final static String KEY_QUESTION = "QUESTION";
    private final static String EXTRA_KEY_SHOWN = "Shown";
    TextView mTextViewAnswer;
    Button mButtonShow;

    TextView mApiLev;

    boolean wasShown;
    boolean mAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
        {
            setAnswerShown(savedInstanceState.getBoolean(EXTRA_KEY_SHOWN));
        }
        else
        {
            setAnswerShown(false);
        }

        setContentView(R.layout.activity_cheat);

        mAnswer = getIntent().getBooleanExtra(EXTRA_KEY_ANSWER,false);

        mTextViewAnswer = (TextView) findViewById(R.id.text_view_answer);
        mButtonShow = (Button) findViewById(R.id.button_show_answer);
        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswer) {
                    mTextViewAnswer.setText("True");
                } else {
                    mTextViewAnswer.setText("False");
                }
                setAnswerShown(true);
                wasShown=true;
            }
        });

        if(getIntent().getBooleanExtra(EXTRA_KEY_SHOWN,false))
        {
            if (mAnswer) {
                mTextViewAnswer.setText("True");
            } else {
                mTextViewAnswer.setText("False");
            }
        }
        setAnswerShown(false);
        wasShown=false;

        mApiLev = (TextView) findViewById(R.id.ApiLev);
        mApiLev.setText("Api Level: " + Build.VERSION.SDK);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean("mAnswer",mAnswer);
        savedInstanceState.putBoolean(EXTRA_KEY_SHOWN,wasShown);

        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        wasShown = savedInstanceState.getBoolean(EXTRA_KEY_SHOWN);

        if(wasShown) {
            if (mAnswer==true) {
                mTextViewAnswer.setText("True");
            } else {
                mTextViewAnswer.setText("False");
            }

        }
        setAnswerShown(wasShown);

    }

    public static Intent newIntent(Context context, boolean answerIsTrue, boolean wasShown)
    {

        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_KEY_ANSWER, answerIsTrue);
        intent.putExtra(EXTRA_KEY_SHOWN,wasShown);
        return intent;

    }

    public static boolean wasAnswerShown(Intent data)
    {
        return data.getBooleanExtra(EXTRA_KEY_SHOWN, false);
    }

    private void setAnswerShown (boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra("Shown", isAnswerShown);

        setResult(RESULT_OK, data);
    }
}
