package ua.edu.sumdu.eleven.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ua.edu.sumdu.eleven.models.Question;
import ua.edu.sumdu.eleven.models.QuestionDao;

public class QuestionRepository {
    private QuestionDao mQuestionDao;
    private LiveData<List<Question>> mAllQuestions;

    public QuestionRepository(Application application) {
        QuestionRoomDatabase db = QuestionRoomDatabase.getDatabase(application);
        mQuestionDao = db.questionDao();
        mAllQuestions = mQuestionDao.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return mAllQuestions;
    }

    public void insert(Question question) {
        QuestionRoomDatabase
                .databaseWriteExecutor
                .execute(() -> mQuestionDao.insert(question));
    }
}
