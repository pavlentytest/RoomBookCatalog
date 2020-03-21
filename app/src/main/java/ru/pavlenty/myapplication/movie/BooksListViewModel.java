package ru.pavlenty.myapplication.movie;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ru.pavlenty.myapplication.db.Book;
import ru.pavlenty.myapplication.db.BookDao;
import ru.pavlenty.myapplication.db.BookDatabase;


public class BooksListViewModel extends AndroidViewModel {
    private BookDao bookDao;
    private LiveData<List<Book>> booksLiveData;

    public BooksListViewModel(@NonNull Application application) {
        super(application);
        bookDao = BookDatabase.getDatabase(application).movieDao();
        booksLiveData = bookDao.getAllBooks();
    }

    public LiveData<List<Book>> getMoviesList() {
        return booksLiveData;
    }

    public void insert(Book... books) {
        bookDao.insert(books);
    }

    public void update(Book book) {
        bookDao.update(book);
    }

    public void deleteAll() {
        bookDao.deleteAll();
    }
}
