package org.armstrong.ika.digitalbibleapp.Notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.NotesDb.NotesEntities;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NotesFragmentAdapter extends RecyclerView.Adapter<NotesFragmentAdapter.CustomViewHolder> {

    private List<NotesEntities> notes = Collections.emptyList(); //cached copy
    private int TextSize;

    public NotesFragmentAdapter(int textsize) {
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notes_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final NotesEntities notesEntities = notes.get(position);

        //customViewHolder.textDate.setText(notesEntities.getDate());
        //customViewHolder.textDate.setTextSize(TextSize - 4);

        customViewHolder.textRef.setText(notesEntities.getRef());
        customViewHolder.textRef.setTextSize(TextSize);

        customViewHolder.textNote.setText(notesEntities.getText());
        customViewHolder.textNote.setTextSize(TextSize);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView textDate;
        protected TextView textRef;
        protected TextView textNote;

        public CustomViewHolder(final View view) {
            super(view);

            //this.textDate = view.findViewById(R.id.textDate);
            this.textRef = view.findViewById(R.id.textRef);
            this.textNote = view.findViewById(R.id.textNote);

        }

    }

    public void setNotes(List<NotesEntities> ne) {
        notes = ne;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


}
