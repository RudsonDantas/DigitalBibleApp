package org.armstrong.ika.digitalbibleapp.Version;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionEntities;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.graphics.Color.TRANSPARENT;

public class VersionSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    //protected VersionViewModel versionViewModel;

    private RecyclerView recyclerView;
    private VersionSheetAdapter versionSheetAdapter;
    protected VersionRepository versionRepository;

    List<VersionEntities> values;

    private Switch aSwitch;
    private int version;
    private int color;
    private int textSize;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

        versionRepository = new VersionRepository(getContext());

        values = versionRepository.getAllVersions();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        version = preferenceProvider.getVersion();
        color = preferenceProvider.getColorVar();
        textSize = preferenceProvider.gettextSizeVar();

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.version_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(TRANSPARENT);

        recyclerView = view.findViewById(R.id.VersionFragmentRecyclerView);

        versionSheetAdapter = new VersionSheetAdapter(version, color, textSize);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(versionSheetAdapter);

//        versionViewModel = ViewModelProviders.of(getActivity()).get(VersionViewModel.class);
//
//        versionViewModel.getAllVersions().observe(getActivity(), new Observer<List<VersionEntities>>() {
//            @Override
//            public void onChanged(List<VersionEntities> versionEntities) {
//                versionSheetAdapter.setValues(versionEntities);
//                values = versionEntities;
//            }
//        });

        versionSheetAdapter.setValues(values);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                final VersionEntities versionEntities = values.get(position);

                final int number = versionEntities.getNumber();  // version number

                aSwitch = view.findViewById(R.id.versionSwitch);

                aSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int active = (aSwitch.isChecked()) ? 1 : 0;

                        if (number == version) {
                            active = 1;
                        }

                        // set version active
                        VersionRepository versionRepository = new VersionRepository(getContext());
                        versionRepository.setVersionActive(active, number);// active, version

                    }

                });


            }

            // long click
            public void onLongClick(View view, int position) {
                // do nothing

            }

        }));

    }


}
