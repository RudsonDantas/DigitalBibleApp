package org.armstrong.ika.digitalbibleapp.Highlight;

import android.graphics.Color;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class HighlightFragmentAdapter extends RecyclerView.Adapter<HighlightFragmentAdapter.CustomViewHolder> {

    private List<HighlightEntities> highlights = Collections.emptyList(); //cached copy
    private int TextSize;

    public HighlightFragmentAdapter(int textsize) {
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.highlight_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        HighlightEntities highlightEntities = highlights.get(position);

        String ref = highlightEntities.getAbbreviation() + " "
                + highlightEntities.getBookname() + " "
                + highlightEntities.getChapter() + ":"
                + highlightEntities.getVerse();

        customViewHolder.highlightRef.setText(ref);
        customViewHolder.highlightRef.setTextSize(TextSize);
        customViewHolder.highlightRef.setTextColor(Color.DKGRAY);

        customViewHolder.highlightText.setText(highlightEntities.getText());
        customViewHolder.highlightText.setTextSize(TextSize);
        customViewHolder.highlightText.setTextColor(Color.DKGRAY);

        if (highlightEntities.getColor() !=-1) {
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(highlightEntities.getText());
            spanText.setSpan(new BackgroundColorSpan(highlightEntities.getColor()), 0, highlightEntities.getText().length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            customViewHolder.highlightText.setText(spanText);
        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView highlightRef;
        protected TextView highlightText;

        public CustomViewHolder(final View view) {
            super(view);

            this.highlightRef = view.findViewById(R.id.highlightRef);
            this.highlightText = view.findViewById(R.id.highlightText);

        }

    }

    public void setValues(List<HighlightEntities> highlights) {
        this.highlights = highlights;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return highlights.size();
    }


}
