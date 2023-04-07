package ua.edu.sumdu.eleven.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ua.edu.sumdu.eleven.BuildConfig;
import ua.edu.sumdu.eleven.R;
import ua.edu.sumdu.eleven.models.Question;

public class QuestionActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            BuildConfig.APPLICATION_ID + "_QUESTION";

    private TextView mTxtQuestion, mTxtAnswer;

    private Question mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mTxtQuestion = findViewById(R.id.txt_question);
        mTxtAnswer = findViewById(R.id.txt_answer);

        if (getIntent().hasExtra(EXTRA_REPLY)) {
            mQuestion = (Question) getIntent().getSerializableExtra(EXTRA_REPLY);
            mTxtQuestion.setText(mQuestion.getQuestion());
            mTxtAnswer.setText(mQuestion.getAnswer());
        }
    }

    public void onDoneClick(View view) {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(mTxtQuestion.getText())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            if (mQuestion == null) {
                mQuestion = new Question(
                        mTxtQuestion.getText().toString(),
                        mTxtAnswer.getText().toString()
                );
            } else {
                mQuestion.setQuestion(mTxtQuestion.getText().toString());
                mQuestion.setAnswer(mTxtAnswer.getText().toString());
            }
            intent.putExtra(EXTRA_REPLY, mQuestion);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}