package ua.edu.sumdu.eleven.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ua.edu.sumdu.eleven.R;
import ua.edu.sumdu.eleven.models.Question;
import ua.edu.sumdu.eleven.viewmodels.QuestionViewModel;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_ITEM_REQUEST_CODE = 1;

    private QuestionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.lst_questions);
        final QuestionListAdapter adapter = new QuestionListAdapter(
                this,
                new QuestionListAdapter.OnClickListener() {
                    @Override
                    public void onClick(Question question) {
                        showQuestion(question);
                    }

                    @Override
                    public void onDelete(Question question) {
                        viewModel.delete(question);
                    }
                }
        );
        list.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        viewModel.getAllQuestions().observe(this, adapter::setItems);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showQuestion(null));
    }

    private void showQuestion(Question question) {
        Intent intent = new Intent(this, QuestionActivity.class);
        if (question != null) {
            intent.putExtra(QuestionActivity.EXTRA_REPLY, question);
        }
        startActivityForResult(intent, NEW_ITEM_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.hasExtra(QuestionActivity.EXTRA_REPLY)
        ) {
            Question question = (Question) data.getSerializableExtra(QuestionActivity.EXTRA_REPLY);
            if (question.getId() == 0) {
                viewModel.insert(question);
            } else {
                viewModel.update(question);
            }
        }
    }
}