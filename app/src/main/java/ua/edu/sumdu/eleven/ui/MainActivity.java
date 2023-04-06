package ua.edu.sumdu.eleven.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

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
        final QuestionListAdapter adapter = new QuestionListAdapter(this);
        list.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        viewModel.getAllQuestions().observe(this, adapter::setItems);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuestionActivity.class);
            startActivityForResult(intent, NEW_ITEM_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.hasExtra(QuestionActivity.EXTRA_REPLY)
        ) {
            Question question = (Question) data.getSerializableExtra(QuestionActivity.EXTRA_REPLY);
            viewModel.insert(question);
        }
    }
}