package org.armstrong.ika.digitalbibleapp.Notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NotesSheetAdapter extends RecyclerView.Adapter<NotesSheetAdapter.CustomViewHolder> {

        private List<NotesSheetModel> menuList;
        private int TextSize;

        public NotesSheetAdapter(List<NotesSheetModel> menulist, int textsize) {
            this.menuList = menulist;
            this.TextSize = textsize;

        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.notes_sheet_item, viewGroup, false);

            CustomViewHolder viewHolder = new CustomViewHolder((view));

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

            final NotesSheetModel notesSheetModel = menuList.get(position);

            customViewHolder.notesSheetItem.setText(notesSheetModel.getNotesSheetMenuText());
            customViewHolder.notesSheetItem.setTextSize(TextSize);

        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            protected TextView notesSheetItem;

            public CustomViewHolder(final View view) {
                super(view);

                this.notesSheetItem = view.findViewById(R.id.notesSheetItem);

            }

        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }


    }
