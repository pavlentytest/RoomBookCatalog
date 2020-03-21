package ru.pavlenty.myapplication.director;

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
import ru.pavlenty.myapplication.db.BookDatabase;

public class AuthorSaveDialogFragment extends DialogFragment {
    private Context context;
    private String authorFullName;

    private static final String EXTRA_AUTHOR_FULL_NAME = "author_full_name";
    public static final String TAG_DIALOG_AUTHOR_SAVE = "author_director_save";

    public static AuthorSaveDialogFragment newInstance(String directorFullName) {
        AuthorSaveDialogFragment fragment = new AuthorSaveDialogFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_AUTHOR_FULL_NAME, directorFullName);
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
        authorFullName = args.getString(EXTRA_AUTHOR_FULL_NAME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_director, null);
        final EditText directorEditText = view.findViewById(R.id.etDirectorFullName);
        if (authorFullName != null) {
            directorEditText.setText(authorFullName);
            directorEditText.setSelection(authorFullName.length());
        }

        alertDialogBuilder.setView(view)
                .setTitle(getString(R.string.dialog_director_title))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDirector(directorEditText.getText().toString());
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

    private void saveDirector(String fullName) {
        if (TextUtils.isEmpty(fullName)) {
            return;
        }

        AuthorDao authorDao = BookDatabase.getDatabase(context).directorDao();

        if (authorFullName != null) {
            Author authorToUpdate = authorDao.findDirectorByName(authorFullName);
            if (authorToUpdate != null) {
                if (!authorToUpdate.fullName.equals(fullName)) {
                    authorToUpdate.fullName = fullName;
                    authorDao.update(authorToUpdate);
                }
            }
        } else {
            authorDao.insert(new Author(fullName));
        }
    }
}