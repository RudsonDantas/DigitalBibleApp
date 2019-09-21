package org.armstrong.ika.digitalbibleapp.Searches.History;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.SearchesDb.SearchesEntities;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SearchesHistoryAdapter extends RecyclerView.Adapter<SearchesHistoryAdapter.CustomViewHolder> {

    private List<SearchesEntities> values;
    private int TextSize;

    public SearchesHistoryAdapter(List<SearchesEntities> values, int textsize ) {
        this.values = values;
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.searches_history_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final SearchesEntities searchesEntities = values.get(position);

        customViewHolder.historyItem.setText(searchesEntities.getText());
        customViewHolder.historyItem.setTextSize(TextSize);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView historyItem;

        public CustomViewHolder(final View view) {
            super(view);

            this.historyItem = view.findViewById(R.id.historyText);

        }

    }

    @Override
    public int getItemCount() {
        return values.size();
    }


}
