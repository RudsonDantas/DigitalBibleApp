package org.armstrong.ika.digitalbibleapp.Highlight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

public class HighlightSheetAdapter extends RecyclerView.Adapter<HighlightSheetAdapter.CustomViewHolder> {

        private List<HighlightEntities> menuList;
        private int TextSize;

        public HighlightSheetAdapter(List<HighlightEntities> menulist, int textsize) {
            this.menuList = menulist;
            this.TextSize = textsize;

        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.highlight_sheet_item, viewGroup, false);

            CustomViewHolder viewHolder = new CustomViewHolder((view));

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

            final HighlightEntities highlightEntities = menuList.get(position);

            customViewHolder.item.setText(highlightEntities.getText());
            customViewHolder.item.setTextSize(TextSize);

        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            protected TextView item;

            public CustomViewHolder(final View view) {
                super(view);

                this.item = view.findViewById(R.id.item);

            }

        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }


    }
