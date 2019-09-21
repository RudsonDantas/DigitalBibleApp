package org.armstrong.ika.digitalbibleapp.Reference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import androidx.recyclerview.widget.RecyclerView;

public class ReferenceFragThreeAdapter extends RecyclerView.Adapter<ReferenceFragThreeAdapter.CustomViewHolder> {

    private int[] verses;
    private int textSize;

    public ReferenceFragThreeAdapter(int[] verses, int textsize) {
        this.verses = verses;
        this.textSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reference_fragthree_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        customViewHolder.referenceVerseNumber.setText(Integer.toString(verses[position]));
        customViewHolder.referenceVerseNumber.setTextSize(textSize);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView referenceVerseNumber;

        public CustomViewHolder(final View view) {
            super(view);

            this.referenceVerseNumber = view.findViewById(R.id.referenceVerseNumber);

        }

    }

    @Override
    public int getItemCount() {
        return verses.length;
    }


}
