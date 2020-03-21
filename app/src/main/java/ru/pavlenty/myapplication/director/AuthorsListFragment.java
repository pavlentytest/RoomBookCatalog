package ru.pavlenty.myapplication.director;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.pavlenty.myapplication.R;
import ru.pavlenty.myapplication.db.Author;

public class AuthorsListFragment extends Fragment {
    private AuthorsListAdapter directorsListAdapter;
    private AuthorsListViewModel directorsViewModel;
    private Context context;

    public static AuthorsListFragment newInstance() {
        return new AuthorsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        directorsListAdapter = new AuthorsListAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directors, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_directors);
        recyclerView.setAdapter(directorsListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    private void initData() {
        directorsViewModel = new ViewModelProvider(this).get(AuthorsListViewModel.class);
        directorsViewModel.getDirectorList().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(@Nullable List<Author> authors) {
                directorsListAdapter.setAuthorList(authors);
            }
        });
    }

    public void removeData() {
        if (directorsViewModel != null) {
            directorsViewModel.deleteAll();
        }
    }
}