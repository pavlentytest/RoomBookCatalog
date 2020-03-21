package ru.pavlenty.myapplication.director;


import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ru.pavlenty.myapplication.db.Author;
import ru.pavlenty.myapplication.db.AuthorDao;
import ru.pavlenty.myapplication.db.BookDatabase;

public class AuthorsListViewModel extends AndroidViewModel {
    private AuthorDao authorDao;
    private LiveData<List<Author>> directorsLiveData;

    public AuthorsListViewModel(@NonNull Application application) {
        super(application);
        authorDao = BookDatabase.getDatabase(application).directorDao();
        directorsLiveData = authorDao.getAllAuthors();
    }

    public LiveData<List<Author>> getDirectorList() {
        return directorsLiveData;
    }

    public void insert(Author... authors) {
        authorDao.insert(authors);
    }

    public void update(Author author) {
        authorDao.update(author);
    }

    public void deleteAll() {
        authorDao.deleteAll();
    }
}
