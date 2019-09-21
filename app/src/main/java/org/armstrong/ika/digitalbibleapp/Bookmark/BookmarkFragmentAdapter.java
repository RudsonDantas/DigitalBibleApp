package org.armstrong.ika.digitalbibleapp.Bookmark;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkEntities;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.Collections;
import java.util.List;

public class BookmarkFragmentAdapter extends RecyclerView.Adapter<BookmarkFragmentAdapter.CustomViewHolder> {

    private List<BookmarkEntities> bookmarks = Collections.emptyList(); //cached copy
    private int TextSize;

    public BookmarkFragmentAdapter(int textsize) {
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bookmark_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final BookmarkEntities bookmarkModel = bookmarks.get(position);

        String holder = bookmarkModel.getAbbreviation()
                + " " + bookmarkModel.getBook_name()
                + " " + bookmarkModel.getChapter() + ":" + bookmarkModel.getVerse();

        customViewHolder.textFour.setText(holder);
        customViewHolder.textFour.setTextSize(TextSize);

        customViewHolder.textSix.setText(bookmarkModel.getText());
        customViewHolder.textSix.setTextSize(TextSize);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        //protected TextView textOne;
        protected TextView textTwo;
        //protected TextView textThree;
        protected TextView textFour;
        //protected TextView textFive;
        protected TextView textSix;

        public CustomViewHolder(final View view) {
            super(view);

            //this.textOne = view.findViewById(R.id.textOne);
            this.textTwo = view.findViewById(R.id.textTwo);
            //this.textThree = view.findViewById(R.id.textThree);
            this.textFour = view.findViewById(R.id.textFour);
            //this.textFive = view.findViewById(R.id.textFive);
            this.textSix = view.findViewById(R.id.textSix);

        }

    }

    public void setBookmarks(List<BookmarkEntities> bookmarks) {
        this.bookmarks = bookmarks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }


}
