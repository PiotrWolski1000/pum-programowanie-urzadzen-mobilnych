package pl.wroc.uni.ift.android.quizactivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class QuestionListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;
    private int adapterIndex;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this will tells the fragment manager to call onCreateOptionsMenu()
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_question_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        QuestionBank questionBank = QuestionBank.getInstance();
        mAdapter = new QuestionAdapter(questionBank.getQuestions());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_quiz, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add:
                Question q = new Question(R.string.question_nowe,true);
                QuestionBank questionBank = QuestionBank.getInstance();
                questionBank.addQuestion(q);
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_quit:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mQuestionTextView;
        private TextView mQuestionIndexTextView;
        private Question mQuestion;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.question_item,parent,false));
            itemView.setOnClickListener(this);
            mQuestionTextView = (TextView) itemView.findViewById(R.id.questions_item);
            mQuestionIndexTextView = (TextView) itemView.findViewById(R.id.questions_item_numer);
        }

        public void bind(Question question, int position)
        {
            mQuestion = question;
            mQuestionTextView.setText(mQuestion.getTextResId());
            mQuestionIndexTextView.setText("#"+Integer.toString(position+1)+" ");
        }

        public void onClick(View view)
        {
            Intent intent  = QuizPager.newIntent(getActivity(), mQuestion.getTextResId());
            adapterIndex = mAdapter.mQuestions.indexOf(mQuestion);
            intent.putExtra("currentQuestion",adapterIndex);
            //Log.d("Click","Click!"+Integer.toString(adapterIndex));
            startActivity(intent);
        }

    }
    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>
    {
        private List<Question> mQuestions;
        public QuestionAdapter(List<Question> questions)
        {
            mQuestions = questions;
        }
        @Override
        public void onBindViewHolder(QuestionHolder holder, int position) {
            Question question = mQuestions.get(position);
            holder.bind(question,position);
        }
        @Override
        public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new QuestionHolder(layoutInflater,parent);
        }
        @Override
        public int getItemCount() {return mQuestions.size();}
    }
}

