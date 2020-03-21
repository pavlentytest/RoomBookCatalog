package ru.pavlenty.myapplication.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BookDao {
    @Query("SELECT * FROM Book WHERE title = :title LIMIT 1")
    Book findMovieByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Book... books);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Book book);

    @Query("DELETE FROM Book")
    void deleteAll();

    @Query("SELECT * FROM Book ORDER BY title ASC")
    LiveData<List<Book>> getAllBooks();
}
