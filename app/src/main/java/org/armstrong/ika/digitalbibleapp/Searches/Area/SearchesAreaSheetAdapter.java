package org.armstrong.ika.digitalbibleapp.Searches.Area;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SearchesAreaSheetAdapter extends RecyclerView.Adapter<SearchesAreaSheetAdapter.CustomViewHolder> {

    private List<SearchesAreaModel> menuList;
    private int textSize;
    private int color;
    private int area;

    public SearchesAreaSheetAdapter(List<SearchesAreaModel> menulist, int textsize, int color, int area) {
        this.menuList = menulist;
        this.textSize = textsize;
        this.color = color;
        this.area = area;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.searches_area_sheet_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final SearchesAreaModel searchesAreaModel = menuList.get(position);

        customViewHolder.areaItem.setText(searchesAreaModel.getItem());
        customViewHolder.areaItem.setTextSize(textSize);

        if (searchesAreaModel.getArea() == this.area) {
            customViewHolder.areaItem.setTypeface(null, Typeface.BOLD_ITALIC);
            customViewHolder.areaItem.setTextColor(color);
        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView areaItem;

        public CustomViewHolder(final View view) {
            super(view);

            this.areaItem = view.findViewById(R.id.searchesAreaSheetItem);

        }

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


}
