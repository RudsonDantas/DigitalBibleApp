package org.armstrong.ika.digitalbibleapp.Reference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import androidx.recyclerview.widget.RecyclerView;

public class ReferenceFragTwoAdapter extends RecyclerView.Adapter<ReferenceFragTwoAdapter.CustomViewHolder> {

    private int[] values;
    private int textSize;

    public ReferenceFragTwoAdapter(int[] values, int textsize) {
        this.values = values;
        this.textSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reference_fragtwo_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        customViewHolder.referenceChapterNumber.setText(Integer.toString(values[position]));
        customViewHolder.referenceChapterNumber.setTextSize(textSize);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView referenceChapterNumber;

        public CustomViewHolder(final View view) {
            super(view);

            this.referenceChapterNumber = view.findViewById(R.id.referenceChapterNumber);

        }

    }

    @Override
    public int getItemCount() {
        return values.length;
    }


}
