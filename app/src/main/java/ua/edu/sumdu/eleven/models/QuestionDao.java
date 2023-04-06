package ua.edu.sumdu.eleven.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Update
    void updateQuestions(Question... questions);

    @Query("DELETE FROM question_table")
    void deleteAll();

    @Query("SELECT * FROM question_table ORDER BY id ASC")
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT * FROM question_table WHERE question LIKE :search")
    List<Question> findQuestion(String search);
}
