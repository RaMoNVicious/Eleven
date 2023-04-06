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
            BuildConfig.APPLICATION_ID + "REPLY";

    private TextView mTxtQuestion, mTxtAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mTxtQuestion = findViewById(R.id.txt_question);
        mTxtAnswer = findViewById(R.id.txt_answer);
    }

    public void onDoneClick(View view) {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(mTxtQuestion.getText())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            String question = mTxtQuestion.getText().toString();
            String answer = mTxtAnswer.getText().toString();
            intent.putExtra(EXTRA_REPLY, new Question(question, answer));
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}