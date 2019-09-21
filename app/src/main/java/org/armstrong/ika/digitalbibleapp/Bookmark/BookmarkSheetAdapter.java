package org.armstrong.ika.digitalbibleapp.Bookmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class BookmarkSheetAdapter extends RecyclerView.Adapter<BookmarkSheetAdapter.CustomViewHolder> {

        private List<BookmarkSheetModel> menuList;
        int TextSize;

        public BookmarkSheetAdapter(List<BookmarkSheetModel> menulist, int textsize) {
            this.menuList = menulist;
            this.TextSize = textsize;

        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.bookmark_sheet_item, viewGroup, false);

            CustomViewHolder viewHolder = new CustomViewHolder((view));

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

            final BookmarkSheetModel bookmarkSheetModel = menuList.get(position);

            customViewHolder.bookmarkSheetItem.setText(bookmarkSheetModel.getMenuText());
            customViewHolder.bookmarkSheetItem.setTextSize(TextSize);

        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            protected TextView bookmarkSheetItem;

            public CustomViewHolder(final View view) {
                super(view);

                this.bookmarkSheetItem = view.findViewById(R.id.BookmakrSheetItem);

            }

        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }


    }
