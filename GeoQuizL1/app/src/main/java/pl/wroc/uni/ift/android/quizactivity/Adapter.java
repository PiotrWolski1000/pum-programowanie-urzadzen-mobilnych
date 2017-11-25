package pl.wroc.uni.ift.android.quizactivity;

/**
 * Created by piotr on 20.11.17.
 */

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private RecyclerView mRecyclerView;

    private List<Question> mDataSet = QuestionBank.getInstance().getQuestions();


    public Adapter(List<Question> mDataset, RecyclerView recyclerViewer) {
        mDataSet = mDataset;
        mRecyclerView = recyclerViewer;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//trzymacz wszystkiego(itemu)
        holder.mTextView.setText(mDataSet.get(position).getTextResId());
        Question question = mDataSet.get(position);
        holder.mTextView.setText(question.getTextResId());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.textView);
        }
    }
}