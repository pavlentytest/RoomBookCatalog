package ru.pavlenty.myapplication.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "author",
        indices = {@Index(value = "full_name", unique = true)})
public class Author {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "did")
    public int id;

    @ColumnInfo(name = "full_name")
    @NonNull
    public String fullName;

    @Ignore
    public int age;

    public Author(@NonNull String fullName) {
        this.fullName = fullName;
    }
}