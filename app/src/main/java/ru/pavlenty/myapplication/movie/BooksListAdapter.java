package ru.pavlenty.myapplication.movie;

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
import ru.pavlenty.myapplication.db.Book;
import ru.pavlenty.myapplication.db.BookDatabase;

import static ru.pavlenty.myapplication.movie.BookSaveDialogFragment.TAG_DIALOG_BOOK_SAVE;

public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.BooksViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Book> bookList;
    private Context context;

    public BooksListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    @Override
    public BooksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = layoutInflater.inflate(R.layout.item_list_movie, parent, false);
        return new BooksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BooksViewHolder holder, int position) {
        if (bookList == null) {
            return;
        }

        final Book book = bookList.get(position);
        if (book != null) {
            holder.titleText.setText(book.title);

            final Author author = BookDatabase.getDatabase(context).directorDao().findDirectorById(book.authorId);
            final String authorFullName;
            if (author != null) {
                holder.directorText.setText(author.fullName);
                authorFullName = author.fullName;
            } else {
                authorFullName = "";
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialogFragment = BookSaveDialogFragment.newInstance(book.title, authorFullName);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG_DIALOG_BOOK_SAVE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (bookList == null) {
            return 0;
        } else {
            return bookList.size();
        }
    }

    static class BooksViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView directorText;

        public BooksViewHolder(View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.tvMovieTitle);
            directorText = itemView.findViewById(R.id.tvMovieDirectorFullName);
        }
    }
}
