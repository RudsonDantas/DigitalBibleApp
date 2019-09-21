package org.armstrong.ika.digitalbibleapp.Compare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CompareAdapter extends RecyclerView.Adapter<CompareAdapter.CustomViewHolder> {

        private List<CompareModel> values = new ArrayList<>();
        int TextSize;

        public CompareAdapter(int textsize) {
            this.TextSize = textsize;

        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.compare_item, viewGroup, false);

            CustomViewHolder viewHolder = new CustomViewHolder((view));

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

            final CompareModel compareModel = values.get(position);

            String header = compareModel.getCompareabbreviation()
                    + " "
                    + compareModel.getComparebook()
                    + " "
                    + compareModel.getComparechapter()
                    + ":"
                    + compareModel.getCompareverse();

            customViewHolder.header.setText(header);
            customViewHolder.header.setTextSize(TextSize);

            customViewHolder.text.setText(compareModel.getComparetext());
            customViewHolder.text.setTextSize(TextSize);

        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            protected TextView header;
            protected TextView text;

            public CustomViewHolder(final View view) {
                super(view);

                this.header = view.findViewById(R.id.compare_header);
                this.text = view.findViewById(R.id.compare_text);

            }

        }

        public void setValues(List<CompareModel> values) {
            this.values = values;
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return values.size();
        }


    }
