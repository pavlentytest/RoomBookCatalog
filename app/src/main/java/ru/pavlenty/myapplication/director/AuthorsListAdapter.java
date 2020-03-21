package ru.pavlenty.myapplication.director;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.pavlenty.myapplication.R;
import ru.pavlenty.myapplication.db.Author;

import static ru.pavlenty.myapplication.director.AuthorSaveDialogFragment.TAG_DIALOG_AUTHOR_SAVE;

public class AuthorsListAdapter extends RecyclerView.Adapter<AuthorsListAdapter.DirectorsViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Author> authorList;
    private Context context;

    public AuthorsListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
        notifyDataSetChanged();
    }

    @Override
    public AuthorsListAdapter.DirectorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = layoutInflater.inflate(R.layout.item_list_director, parent, false);
        return new AuthorsListAdapter.DirectorsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AuthorsListAdapter.DirectorsViewHolder holder, int position) {
        if (authorList == null) {
            return;
        }

        final Author author = authorList.get(position);
        if (author != null) {
            holder.directorText.setText(author.fullName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialogFragment = AuthorSaveDialogFragment.newInstance(author.fullName);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG_DIALOG_AUTHOR_SAVE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (authorList == null) {
            return 0;
        } else {
            return authorList.size();
        }
    }

    static class DirectorsViewHolder extends RecyclerView.ViewHolder {
        private TextView directorText;

        public DirectorsViewHolder(View itemView) {
            super(itemView);
            directorText = itemView.findViewById(R.id.tvDirector);
        }
    }
}
