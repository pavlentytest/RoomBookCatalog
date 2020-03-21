package ru.pavlenty.myapplication.movie;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.pavlenty.myapplication.R;
import ru.pavlenty.myapplication.db.Book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BooksListFragment extends Fragment {

    private BooksListAdapter moviesListAdapter;
    private BooksListViewModel moviesViewModel;
    private Context context;

    public static BooksListFragment newInstance() {
        return new BooksListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        moviesListAdapter = new BooksListAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_movies);
        recyclerView.setAdapter(moviesListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private void initData() {
        moviesViewModel = new ViewModelProvider(this).get(BooksListViewModel.class);
        moviesViewModel.getMoviesList().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                moviesListAdapter.setBookList(books);
            }
        });
    }

    public void removeData() {
        if (moviesListAdapter != null) {
            moviesViewModel.deleteAll();
        }
    }

}
