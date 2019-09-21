package org.armstrong.ika.digitalbibleapp.Active;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionEntities;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActiveSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    private VersionViewModel versionViewModel;

    private RecyclerView recyclerView;
    private ActiveFragmentAdapter activeFragmentAdapter;

    private List<VersionEntities> values;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        preferenceProvider = new PreferenceProvider(getContext());

        int version = preferenceProvider.getVersion();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.active_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.ActiveFragmentRecyclerView);

        activeFragmentAdapter = new ActiveFragmentAdapter(preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(activeFragmentAdapter);

        versionViewModel = ViewModelProviders.of(getActivity()).get(VersionViewModel.class);

        versionViewModel.getActiveVersions(version).observe(getActivity(), new Observer<List<VersionEntities>>() {
            @Override
            public void onChanged(List<VersionEntities> versionEntities) {
                activeFragmentAdapter.setValues(versionEntities);
                values = versionEntities;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                dismiss();

                final VersionEntities versionEntities = values.get(position);

                preferenceProvider.setVersion(versionEntities.getNumber());

                getActivity().finish();
                startActivity(getActivity().getIntent());

            }

            // long click
            public void onLongClick(View view, int position) {
                dismiss();
            }
                // do nothing
        }));

    }

}
