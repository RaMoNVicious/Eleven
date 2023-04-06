package ua.edu.sumdu.eleven.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ua.edu.sumdu.eleven.R;
import ua.edu.sumdu.eleven.models.Question;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionVH> {

    private final LayoutInflater mInflater;

    private List<Question> mItems;

    public QuestionListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setItems(List<Question> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        return new QuestionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionVH holder, int position) {
        if (mItems != null) {
            Question item = mItems.get(position);
            holder.mTxtQuestion.setText(item.getQuestion());
            holder.mTxtAnswer.setText(item.getAnswer());
        } else {
            holder.mTxtQuestion.setText("...");
            holder.mTxtAnswer.setText("...");
        }
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    class QuestionVH extends RecyclerView.ViewHolder {

        private final TextView mTxtQuestion, mTxtAnswer;

        public QuestionVH(@NonNull View itemView) {
            super(itemView);
            mTxtQuestion = itemView.findViewById(R.id.txt_question);
            mTxtAnswer = itemView.findViewById(R.id.txt_answer);
        }
    }
}
