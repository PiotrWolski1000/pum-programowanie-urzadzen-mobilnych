package pl.wroc.uni.ift.android.quizactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private QuestionBank mQuestionBank = QuestionBank.getInstance();
    private List<Question> dataList = new ArrayList<>();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dataList = mQuestionBank.getQuestions();

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new Adapter(dataList, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }
}