package org.armstrong.ika.digitalbibleapp.More;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

public class MoreFragmentAdapter extends RecyclerView.Adapter<MoreFragmentAdapter.CustomViewHolder> {

    private List<MoreModel> moreItems;
    private int TextSize;

    public MoreFragmentAdapter(List<MoreModel> moreItems, int textsize) {
        this.moreItems = moreItems;
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.more_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final MoreModel moreModel = moreItems.get(position);

        customViewHolder.moreItem.setText(moreModel.getMoreText());
        customViewHolder.moreItem.setTextSize(TextSize);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView moreItem;

        public CustomViewHolder(final View view) {
            super(view);

            this.moreItem = view.findViewById(R.id.moreItem);

        }

    }

    @Override
    public int getItemCount() {
        return moreItems.size();
    }


}
