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

public class MoreSizeSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    private RecyclerView recyclerView;
    private MoreSizeSheetAdapter moreSizeSheetAdapter;

    private List<MoreSizeSheetModel> menuItems = new ArrayList<>();
    MoreSizeSheetModel moreSizeSheetModel;

    private int textSize;
    private int color;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

        textSize = preferenceProvider.gettextSizeVar();
        color = preferenceProvider.getColorVar();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        String items[] = {
                getString(R.string.small),
                getString(R.string.medium),
                getString(R.string.large),
                getString(R.string.xlarge)};

        for (int i = 0; i < items.length; i++) {
            moreSizeSheetModel = new MoreSizeSheetModel();
            moreSizeSheetModel.setMenuText(items[i]);
            menuItems.add(moreSizeSheetModel);
        }


        //Set the custom view
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.more_size_sheet_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.MoreSizeSheetRecyclerView);

        moreSizeSheetAdapter = new MoreSizeSheetAdapter(menuItems, textSize, color);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(moreSizeSheetAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, int position) {

                dismiss();

                //moreSizeSheetModel = menuItems.get(position);

                int size = 0;

                switch (position) {

                    case 0: // small
                        size = 16;
                        preferenceProvider.setTextSize(size);
                        break;

                    case 1: // medium
                        size = 18;
                        preferenceProvider.setTextSize(size);
                        break;

                    case 2: // large
                        size = 20;
                        preferenceProvider.setTextSize(size);
                        break;

                    case 3: // xlarge
                        size = 22;
                        preferenceProvider.setTextSize(size);
                        break;

                }

                MoreFragment.getInstance().updateMoreRecyclerView(size);

            }

            public void onLongClick(View view, int position) {
                //do nothing
            }
        }));

    }

}
