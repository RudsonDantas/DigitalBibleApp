package org.armstrong.ika.digitalbibleapp.More;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MoreSizeSheetAdapter extends RecyclerView.Adapter<MoreSizeSheetAdapter.CustomViewHolder> {

    private List<MoreSizeSheetModel> menuList;
    private int textSize;
    private int color;
    private int sizes[] = {16, 18, 20, 22};

    public MoreSizeSheetAdapter(List<MoreSizeSheetModel> menulist, int textsize, int color) {
        this.menuList = menulist;
        this.textSize = textsize;
        this.color = color;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.moresize_sheet_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final MoreSizeSheetModel moreSizeSheetModel = menuList.get(position);

        customViewHolder.moreSizeSheetItem.setText(moreSizeSheetModel.getMenuText());
        customViewHolder.moreSizeSheetItem.setTextSize(sizes[position]);

        if (textSize == sizes[position]) {
            customViewHolder.moreSizeSheetItem.setTypeface(null, Typeface.BOLD_ITALIC);
            customViewHolder.moreSizeSheetItem.setTextColor(color);
        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView moreSizeSheetItem;

        public CustomViewHolder(final View view) {
            super(view);

            this.moreSizeSheetItem = view.findViewById(R.id.moreSizeSheetItem);

        }

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


}
