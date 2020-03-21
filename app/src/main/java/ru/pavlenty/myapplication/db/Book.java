package ru.pavlenty.myapplication.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "book",
        foreignKeys = @ForeignKey(entity = Author.class,
                parentColumns = "did",
                childColumns = "authorId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("title"), @Index("authorId")})
public class Book {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid")
    public int id;

    @ColumnInfo(name = "title")
    @NonNull
    public String title;

    @ColumnInfo(name = "authorId")
    public int authorId;

    public Book(@NonNull String title, int authorId) {
        this.title = title;
        this.authorId = authorId;
    }
}