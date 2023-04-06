package ua.edu.sumdu.eleven.repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ua.edu.sumdu.eleven.models.Question;
import ua.edu.sumdu.eleven.models.QuestionDao;

@Database(entities = {Question.class}, version = 1, exportSchema = false)
public abstract class QuestionRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 2;
    public abstract QuestionDao questionDao();

    private static volatile QuestionRoomDatabase INSTANCE;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static QuestionRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuestionRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    QuestionRoomDatabase.class,
                                    "question_database"
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final QuestionDao mDao;

        List<Pair<String, String>> questions = Arrays.asList(
                new Pair<>("Why?", "Because!"),
                new Pair<>("Who?", "Not me!"),
                new Pair<>("Where is?", "I dunnow!")
        );

        public PopulateDbAsync(QuestionRoomDatabase database) {
            mDao = database.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();

            questions.forEach(item ->
                    mDao.insert(new Question(item.first, item.second))
            );
            return null;
        }
    }

}
