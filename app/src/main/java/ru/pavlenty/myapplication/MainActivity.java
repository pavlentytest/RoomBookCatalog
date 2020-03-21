package ru.pavlenty.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ru.pavlenty.myapplication.db.BookDatabase;
import ru.pavlenty.myapplication.director.AuthorSaveDialogFragment;
import ru.pavlenty.myapplication.director.AuthorsListFragment;
import ru.pavlenty.myapplication.movie.BookSaveDialogFragment;
import ru.pavlenty.myapplication.movie.BooksListFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static ru.pavlenty.myapplication.director.AuthorSaveDialogFragment.TAG_DIALOG_AUTHOR_SAVE;
import static ru.pavlenty.myapplication.movie.BookSaveDialogFragment.TAG_DIALOG_BOOK_SAVE;

public class MainActivity extends AppCompatActivity {

    private boolean BOOKS_SHOWN = true;
    private Fragment shownFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar(getString(R.string.app_name));
        initView();
        if (savedInstanceState == null) {
            showFragment(BooksListFragment.newInstance());
        }
    }

    public void setToolbar(@NonNull String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_movies:
                        BOOKS_SHOWN = true;
                        showFragment(BooksListFragment.newInstance());
                        return true;
                    case R.id.navigation_directors:
                        BOOKS_SHOWN = false;
                        showFragment(AuthorsListFragment.newInstance());
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveDialog();
            }
        });
    }

    private void showFragment(final Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentHolder, fragment);
        fragmentTransaction.commitNow();
        shownFragment = fragment;
    }

    private void showSaveDialog() {
        DialogFragment dialogFragment;
        String tag;
        if (BOOKS_SHOWN) {
            dialogFragment = BookSaveDialogFragment.newInstance(null, null);
            tag = TAG_DIALOG_BOOK_SAVE;
        } else {
            dialogFragment = AuthorSaveDialogFragment.newInstance(null);
            tag = TAG_DIALOG_AUTHOR_SAVE;
        }

        dialogFragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_delete_list_data) {
            deleteCurrentListData();
            return true;
        } else if (id == R.id.action_re_create_database) {
            reCreateDatabase();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCurrentListData() {
        if (BOOKS_SHOWN) {
            ((BooksListFragment) shownFragment).removeData();
        } else {
            ((AuthorsListFragment) shownFragment).removeData();
        }
    }

    private void reCreateDatabase() {
        BookDatabase.getDatabase(this).clearDb();
    }
}
