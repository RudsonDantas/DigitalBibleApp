package org.armstrong.ika.digitalbibleapp.More;

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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoreColorPickerSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    private RecyclerView recyclerView;
    private MoreColorPickerSheetAdapter moreColorPickerSheetAdapter;

    private List<MoreColorPickerModel> menuItems = new ArrayList<>();
    private MoreColorPickerModel moreColorPickerModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        String items[] = {getString(R.string.flower), getString(R.string.circle)};

        int x = 0;
        for (String i : items) {
            moreColorPickerModel = new MoreColorPickerModel();
            moreColorPickerModel.setText(i);
            moreColorPickerModel.setNumber(x);
            menuItems.add(moreColorPickerModel);
            x++;
        }

        //Set the custom view
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getContext()).inflate(R.layout.notes_sheet_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.NotesSheetRecyclerView);

        moreColorPickerSheetAdapter = new MoreColorPickerSheetAdapter(menuItems,
                preferenceProvider.getWheelStyle(),
                preferenceProvider.getColorVar(),
                preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(moreColorPickerSheetAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, int position) {

                dismiss();

                String ws = "0";

                switch (position) {
                    case 0: // flower
                        ws = "0";
                        break;
                    case 1: // circle
                        ws = "1";
                        break;

                }

                preferenceProvider.setWheelStyle(ws);

            }

            public void onLongClick(View view, int position) {
                //do nothing
            }
        }));

    }

}
