package org.armstrong.ika.digitalbibleapp.Active;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionEntities;

import java.util.Collections;
import java.util.List;

public class ActiveFragmentAdapter extends RecyclerView.Adapter<ActiveFragmentAdapter.CustomViewHolder> {

    private List<VersionEntities> values = Collections.emptyList(); //cached copy
    private int TextSize;

    public ActiveFragmentAdapter(int textsize) {
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.active_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final VersionEntities versionModel = values.get(position);

        customViewHolder.active_name.setText(versionModel.getVerName());
        customViewHolder.active_name.setTextSize(TextSize);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView active_name;

        public CustomViewHolder(final View view) {
            super(view);

            this.active_name = view.findViewById(R.id.active_name);

        }

    }

    public void setValues(List<VersionEntities> values){
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


}
