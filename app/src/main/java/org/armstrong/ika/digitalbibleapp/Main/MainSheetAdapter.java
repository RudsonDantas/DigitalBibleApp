package org.armstrong.ika.digitalbibleapp.Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MainSheetAdapter extends RecyclerView.Adapter<MainSheetAdapter.CustomViewHolder> {

        private List<MainSheetModel> menuList;
        private int TextSize;

        public MainSheetAdapter(List<MainSheetModel> menuList, int textsize) {
            this.menuList = menuList;
            this.TextSize = textsize;

        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.main_sheet_item, viewGroup, false);

            CustomViewHolder viewHolder = new CustomViewHolder((view));

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

            final MainSheetModel mainSheetModel = menuList.get(position);

            customViewHolder.menuItem.setText(mainSheetModel.getMenuItem());
            customViewHolder.menuItem.setTextSize(TextSize);

        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            protected TextView menuItem;

            public CustomViewHolder(final View view) {
                super(view);

                this.menuItem = view.findViewById(R.id.menuItem);

            }

        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }


    }
