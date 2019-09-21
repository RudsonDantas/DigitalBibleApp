package org.armstrong.ika.digitalbibleapp.Completed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.armstrong.ika.digitalbibleapp.CompletedDb.CompletedEntities;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.Collections;
import java.util.List;

public class CompletedFragmentAdapter extends RecyclerView.Adapter<CompletedFragmentAdapter.CustomViewHolder> {

    private List<CompletedEntities> completed = Collections.emptyList(); //cached copy
    private int TextSize;

    public CompletedFragmentAdapter(int textsize) {
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.completed_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        CompletedEntities completedEntities = completed.get(position);

        StringBuilder ref = new StringBuilder();
        ref.append(completedEntities.getAbbreviation());
        ref.append(" ");
        ref.append(completedEntities.getBookname());

        customViewHolder.completedRef.setText(ref);
        customViewHolder.completedRef.setTextSize(TextSize);
//        customViewHolder.completedRef.setTextColor(Color.DKGRAY);

        customViewHolder.completedText.setText(completedEntities.getTime());
        customViewHolder.completedText.setTextSize(TextSize -3);
        //customViewHolder.completedText.setTextColor(Color.DKGRAY);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView completedRef;
        protected TextView completedText;

        public CustomViewHolder(final View view) {
            super(view);

            this.completedRef = view.findViewById(R.id.completedRef);
            this.completedText = view.findViewById(R.id.completedText);

        }

    }

    public void setValues(List<CompletedEntities> completed) {
        this.completed = completed;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return completed.size();
    }


}
