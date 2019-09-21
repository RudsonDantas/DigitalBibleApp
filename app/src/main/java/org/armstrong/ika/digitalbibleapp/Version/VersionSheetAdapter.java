package org.armstrong.ika.digitalbibleapp.Version;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionEntities;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class VersionSheetAdapter extends RecyclerView.Adapter<VersionSheetAdapter.CustomViewHolder> {

    private List<VersionEntities> values = Collections.emptyList(); //cached copy

    private int version;
    private int color;
    private int TextSize;

    public VersionSheetAdapter(int version, int color, int textsize) {

        this.version = version;
        this.color = color;
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.version_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final VersionEntities versionModel = values.get(position);

        boolean status;
        int a = versionModel.getActive();
        status = (a == 1) ? true : false;

        customViewHolder.versionName.setText(versionModel.getVerName());
        customViewHolder.versionName.setTextSize(TextSize);
        customViewHolder.versionSwitch.setChecked(status);

        if(versionModel.getNumber() == version) {
            customViewHolder.versionName.setTypeface(null, Typeface.BOLD_ITALIC);
            customViewHolder.versionName.setTextColor(color);
        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView versionName;
        protected Switch versionSwitch;

        public CustomViewHolder(final View view) {
            super(view);

            this.versionName = view.findViewById(R.id.versionName);
            this.versionSwitch = view.findViewById(R.id.versionSwitch);

        }

    }

    public void setValues(List<VersionEntities> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


}
