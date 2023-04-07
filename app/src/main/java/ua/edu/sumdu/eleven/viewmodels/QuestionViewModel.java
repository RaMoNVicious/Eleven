package ua.edu.sumdu.eleven.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ua.edu.sumdu.eleven.models.Question;
import ua.edu.sumdu.eleven.repositories.QuestionRepository;

public class QuestionViewModel extends AndroidViewModel {

    private final QuestionRepository mRepository;

    private final LiveData<List<Question>> mAllQuestions;

    public QuestionViewModel(@NonNull Application application) {
        super(application);

        mRepository = new QuestionRepository(application);
        mAllQuestions = mRepository.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return mAllQuestions;
    }

    public void insert(Question question) {
        mRepository.insert(question);
    }

    public void update(Question... questions) {
        mRepository.update(questions);
    }

    public void delete(Question question) {
        mRepository.delete(question);
    }
}
