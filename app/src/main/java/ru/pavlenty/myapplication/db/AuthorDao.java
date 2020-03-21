package ru.pavlenty.myapplication.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AuthorDao {
    @Query("SELECT * FROM Author WHERE did = :id LIMIT 1")
    Author findDirectorById(int id);

    @Query("SELECT * FROM Author WHERE full_name = :fullName LIMIT 1")
    Author findDirectorByName(String fullName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Author author);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Author... authors);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Author author);

    @Query("DELETE FROM Author")
    void deleteAll();

    @Query("SELECT * FROM Author ORDER BY full_name ASC")
    LiveData<List<Author>> getAllAuthors();
}