package ru.pavlenty.myapplication.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Book.class, Author.class}, version = 2)
public abstract class BookDatabase extends RoomDatabase {
    private static BookDatabase INSTANCE;
    private static final String DB_NAME = "book.db";

    public static BookDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // Лучше так не делать (используйте AsyncTask)
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
    }

    public abstract BookDao movieDao();

    public abstract AuthorDao directorDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final BookDao bookDao;
        private final AuthorDao authorDao;

        public PopulateDbAsync(BookDatabase instance) {
            bookDao = instance.movieDao();
            authorDao = instance.directorDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookDao.deleteAll();
            authorDao.deleteAll();

            Author authorOne = new Author("Шилдт Герберт");
            Author authorTwo = new Author("Эккель Брюс");
            Author authorThree = new Author("Сьерра Кэти, Бэйтс Берт");
            Author authorFour = new Author("Лафоре Роберт");

            final int dIdOne = (int) authorDao.insert(authorOne);
            Book bookOne = new Book("Java. Полное руководство", dIdOne);
            Book bookOne1 = new Book("Java. Руководство для начинающих. Современные методы создания, компиляции и выполнения программ на Java", dIdOne);
            final int dIdTwo = (int) authorDao.insert(authorTwo);
            Book bookTwo = new Book("Философия Java", dIdTwo);
            Book bookThree = new Book("Thinking in c++", dIdTwo);
            Book bookFour = new Book("Изучаем Java ", (int) authorDao.insert(authorThree));
            Book bookFive = new Book("Структуры данных и алгоритмы в Java",(int) authorDao.insert(authorFour) );

            bookDao.insert(bookOne, bookOne1, bookTwo, bookThree, bookFour, bookFive);

            return null;
        }
    }
}