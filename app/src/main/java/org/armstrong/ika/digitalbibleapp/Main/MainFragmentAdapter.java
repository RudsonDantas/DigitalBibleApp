package org.armstrong.ika.digitalbibleapp.Main;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesEntities;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;
import org.armstrong.ika.digitalbibleapp.NotesDb.NotesEntities;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.Collections;
import java.util.List;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.CustomViewHolder> {

    private List<BiblesEntities> values = Collections.emptyList(); //cached copy
    private List<HighlightEntities> highlights;
    private List<NotesEntities> notes;
    private BiblesEntities value;
    private int TextSize;

    public MainFragmentAdapter(List<HighlightEntities> highlights, List<NotesEntities> notes, int textsize) {
        this.highlights = highlights;
        this.notes = notes;
        this.TextSize = textsize;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView textOne;
        public TextView textTwo;

        public CustomViewHolder(View view) {
            super(view);

            this.textOne = view.findViewById(R.id.textOne);
            this.textTwo = view.findViewById(R.id.textTwo);

        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_item, viewGroup, false);

        CustomViewHolder customViewHolder = new CustomViewHolder((view));

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        value = values.get(position);

        customViewHolder.textOne.setText(Integer.toString(value.getV())); // verse number
        customViewHolder.textOne.setTextSize(TextSize - 5);
        customViewHolder.textOne.setTextColor(Color.DKGRAY);

        if (highlights.size() > 0) {

            for (int i = 0; i < highlights.size(); i++) {

                HighlightEntities highlightEntities = highlights.get(i);

                if (value.getV() == highlightEntities.getVerse()) {

                    if(value.getT().length() > 0) {

                        Spannable spannable = new SpannableString(value.getT());
                        spannable.setSpan(new BackgroundColorSpan(highlightEntities.getColor()), 0, value.getT().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        customViewHolder.textTwo.setText(spannable);

                    }

                    break;

                } else {

                    customViewHolder.textTwo.setText(value.getT());

                }

            }

        } else {

            customViewHolder.textTwo.setText(value.getT());

        }

        customViewHolder.textTwo.setTextSize(TextSize);
        //customViewHolder.textTwo.setTextColor(Color.DKGRAY);

        // note
        String n = addNote();
        if(n.length() > 0) {

            Spannable spannable = new SpannableString(n);
            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, n.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            customViewHolder.textTwo.append(spannable);
        }


    }

    private String addNote() {

        String note = "";

        if (notes.size() > 0) {

            for (int i = 0; i < notes.size(); i++) {

                NotesEntities notesEntities = notes.get(i);

                if (value.getV() == notesEntities.getVerse()) {

                    note = "{note}";

                }

            }

        }

        return note;
    }

    public void setValues(List<BiblesEntities> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


}
