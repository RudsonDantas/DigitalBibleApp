package org.armstrong.ika.digitalbibleapp.Common.Menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.Common.MenuItemsModel;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.CustomViewHolder> {

    private List<MenuItemsModel> menuList;
    private int TextSize;

    public MenuItemsAdapter(List<MenuItemsModel> menulist, int textsize) {
        this.menuList = menulist;
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final MenuItemsModel menuItemsModel = menuList.get(position);

        customViewHolder.menuItem.setText(menuItemsModel.getMenuItem());
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
