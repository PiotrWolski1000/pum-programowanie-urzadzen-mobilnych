package pl.wroc.uni.ift.android.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;


public class CheatActivity extends AppCompatActivity {

    private final static String EXTRA_KEY_ANSWER = "Answer";
    private final static String KEY_QUESTION = "QUESTION";
    private final static String EXTRA_KEY_SHOWN = "wasShown";
    private final static String KEY_TOKENS = "CheatTokens";
    TextView mTextViewAnswer;
    TextView mCheatTokensTextView;
    Button mButtonShow;

    boolean wasShown;
    boolean mAnswer;
    int mCheatTokens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
        {
            setAnswerShown(savedInstanceState.getBoolean("wasShown"));
        }
        else
        {
            setAnswerShown(false);
        }

        setContentView(R.layout.activity_cheat);

        mCheatTokensTextView = (TextView) findViewById(R.id.cheatTokensTextView);
        mCheatTokens =  getIntent().getIntExtra(KEY_TOKENS,0);
        updateTokenCount();

        mAnswer = getIntent().getBooleanExtra(EXTRA_KEY_ANSWER,false);

        mTextViewAnswer = (TextView) findViewById(R.id.text_view_answer);
        mButtonShow = (Button) findViewById(R.id.button_show_answer);
        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int toastMessageId;
                if (mCheatTokens<=0)
                {
                    toastMessageId = R.string.NoTokens;
                }
                else
                {
                    if (mAnswer) {
                        mTextViewAnswer.setText("Prawda");
                    } else {
                        mTextViewAnswer.setText("Fałsz");
                    }
                    setAnswerShown(true);
                    wasShown=true;
                    toastMessageId = R.string.UsedToken;
                    mCheatTokens--;
                    updateTokenCount();
                }
                Toast.makeText(CheatActivity.this, toastMessageId, Toast.LENGTH_SHORT).show();

            }
        });

        if(getIntent().getBooleanExtra(EXTRA_KEY_SHOWN,false))
        {
            if (mAnswer) {
                mTextViewAnswer.setText("Prawda");
            } else {
                mTextViewAnswer.setText("Fałsz");
            }
        }
        setAnswerShown(false);
        wasShown=false;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean("mAnswer",mAnswer);
        savedInstanceState.putBoolean(EXTRA_KEY_SHOWN,wasShown);
        savedInstanceState.putInt(KEY_TOKENS,mCheatTokens);
        //Log.i("save_wasShown","Save Instance wasShown: "+Boolean.toString(wasShown));
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        wasShown = savedInstanceState.getBoolean(EXTRA_KEY_SHOWN);
        //Log.i("restore_wasShown_before","Restore Instance wasShown before if: "+Boolean.toString(wasShown));
        if(wasShown) {
            if (mAnswer==true) {
                mTextViewAnswer.setText("Prawda");
            } else {
                mTextViewAnswer.setText("Fałsz");
            }

        }
        setAnswerShown(wasShown);

        mCheatTokens = savedInstanceState.getInt(KEY_TOKENS);
        updateTokenCount();
        //Log.i("restore_wasShown_after","Restore Instance wasShown after if: "+Boolean.toString(wasShown));
    }

    public static boolean wasAnswerShown(Intent data)
    {
        return data.getBooleanExtra(EXTRA_KEY_SHOWN, false);
    }

    public static Intent newIntent(Context context, boolean answerIsTrue, boolean wasShown, int CheatTokens)
    {

        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_KEY_ANSWER, answerIsTrue);
        intent.putExtra(EXTRA_KEY_SHOWN,wasShown);
        intent.putExtra(KEY_TOKENS,CheatTokens);
        return intent;
    }


    private void setAnswerShown (boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra("wasShown", isAnswerShown);
        setResult(RESULT_OK, data);
    }


    private void updateTokenCount()
    {
        mCheatTokensTextView.setText("Dostępnych podejrzeń: "+Integer.toString(mCheatTokens));
        Intent data = new Intent();
        data.putExtra(KEY_TOKENS, mCheatTokens);
        setResult(RESULT_OK, data);

    }

    public static int getCheatTokens(Intent data)
    {
        int a = data.getIntExtra(KEY_TOKENS, 0);
        Log.d("Tokens get",Integer.toString(a));
        return a;

    }

}
