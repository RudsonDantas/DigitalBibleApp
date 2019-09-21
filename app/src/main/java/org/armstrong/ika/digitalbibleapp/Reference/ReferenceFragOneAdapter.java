package org.armstrong.ika.digitalbibleapp.Reference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.LangKeyDb.LangEntities;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ReferenceFragOneAdapter extends RecyclerView.Adapter<ReferenceFragOneAdapter.CustomViewHolder> {

    private List<LangEntities> values = Collections.emptyList(); //cached copy
    private int TextSize;

    public ReferenceFragOneAdapter(int textsize) {
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reference_fragone_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final LangEntities langEntities = values.get(position);

        customViewHolder.referenceName.setText(langEntities.getName());
        customViewHolder.referenceName.setTextSize(TextSize);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView referenceName;

        public CustomViewHolder(final View view) {
            super(view);

            this.referenceName = view.findViewById(R.id.reference_name);

        }

    }

    public void setValues(List<LangEntities> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


}
