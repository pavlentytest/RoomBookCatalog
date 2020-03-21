package ru.pavlenty.myapplication.movie;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import ru.pavlenty.myapplication.R;
import ru.pavlenty.myapplication.db.Author;
import ru.pavlenty.myapplication.db.AuthorDao;
import ru.pavlenty.myapplication.db.Book;
import ru.pavlenty.myapplication.db.BookDao;
import ru.pavlenty.myapplication.db.BookDatabase;

public class BookSaveDialogFragment extends DialogFragment {
    private Context context;
    private String movieTitleExtra;
    private String movieDirectorFullNameExtra;

    private static final String EXTRA_MOVIE_TITLE = "movie_title";
    private static final String EXTRA_MOVIE_DIRECTOR_FULL_NAME = "movie_director_full_name";
    public static final String TAG_DIALOG_BOOK_SAVE = "dialog_movie_save";

    public static BookSaveDialogFragment newInstance(final String movieTitle, final String movieDirectorFullName) {
        BookSaveDialogFragment fragment = new BookSaveDialogFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_MOVIE_TITLE, movieTitle);
        args.putString(EXTRA_MOVIE_DIRECTOR_FULL_NAME, movieDirectorFullName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        movieTitleExtra = args.getString(EXTRA_MOVIE_TITLE);
        movieDirectorFullNameExtra = args.getString(EXTRA_MOVIE_DIRECTOR_FULL_NAME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_movie, null);
        final EditText movieEditText = view.findViewById(R.id.etMovieTitle);
        final EditText movieDirectorEditText = view.findViewById(R.id.etMovieDirectorFullName);
        if (movieTitleExtra != null) {
            movieEditText.setText(movieTitleExtra);
            movieEditText.setSelection(movieTitleExtra.length());
        }
        if (movieDirectorFullNameExtra != null) {
            movieDirectorEditText.setText(movieDirectorFullNameExtra);
            movieDirectorEditText.setSelection(movieDirectorFullNameExtra.length());
        }

        alertDialogBuilder.setView(view)
                .setTitle(getString(R.string.dialog_movie_title))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveMovie(movieEditText.getText().toString(), movieDirectorEditText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return alertDialogBuilder.create();
    }

    private void saveMovie(String movieTitle, String movieDirectorFullName) {
        if (TextUtils.isEmpty(movieTitle) || TextUtils.isEmpty(movieDirectorFullName)) {
            return;
        }

        AuthorDao authorDao = BookDatabase.getDatabase(context).directorDao();
        BookDao bookDao = BookDatabase.getDatabase(context).movieDao();

        int authorId = -1;
        if (movieDirectorFullNameExtra != null) {

            Author authorToUpdate = authorDao.findDirectorByName(movieDirectorFullNameExtra);
            if (authorToUpdate != null) {
                authorId = authorToUpdate.id;

                if (!authorToUpdate.fullName.equals(movieDirectorFullName)) {
                    authorToUpdate.fullName = movieDirectorFullName;
                    authorDao.update(authorToUpdate);
                }
            }
        } else {

            Author newAuthor = authorDao.findDirectorByName(movieDirectorFullName);
            if (newAuthor == null) {
                authorId = (int) authorDao.insert(new Author(movieDirectorFullName));
            } else {
                authorId = newAuthor.id;
            }
        }

        if (movieTitleExtra != null) {

            Book bookToUpdate = bookDao.findMovieByTitle(movieTitleExtra);
            if (bookToUpdate != null) {
                if (!bookToUpdate.title.equals(movieTitle)) {
                    bookToUpdate.title = movieTitle;
                    if (authorId != -1) {
                        bookToUpdate.authorId = authorId;
                    }
                    bookDao.update(bookToUpdate);
                }
            }
        } else {

            Book newBook = bookDao.findMovieByTitle(movieTitle);
            if (newBook == null) {
                bookDao.insert(new Book(movieTitle, authorId));
            } else {
                if (newBook.authorId != authorId) {
                    newBook.authorId = authorId;
                    bookDao.update(newBook);
                }
            }
        }
    }
}
