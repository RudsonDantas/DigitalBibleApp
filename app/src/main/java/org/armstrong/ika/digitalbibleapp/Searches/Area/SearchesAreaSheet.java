package org.armstrong.ika.digitalbibleapp.Searches.Area;

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
import org.armstrong.ika.digitalbibleapp.Searches.SearchesActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchesAreaSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    private RecyclerView recyclerView;
    private SearchesAreaSheetAdapter searchesAreaSheetAdapter;

    private List<SearchesAreaModel> menuItems = new ArrayList<>();
    SearchesAreaModel searchesAreaModel;

    private int textSize;
    private int color;

    private int searchArea;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

        SearchesActivity.getInstance().searchView.clearFocus();

        color = preferenceProvider.getColorVar();
        textSize = preferenceProvider.gettextSizeVar();
        searchArea = preferenceProvider.getSearchArea();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        String items[] = {
                getString(R.string.moses),
                getString(R.string.old_testament),
                getString(R.string.wisdom),
                getString(R.string.major),
                getString(R.string.minor),
                getString(R.string.new_testament),
                getString(R.string.paul),
                getString(R.string.general),
                getString(R.string.rev)
        };

        int x = 0;
        for (String i : items) {
            searchesAreaModel = new SearchesAreaModel();
            searchesAreaModel.setItem(i);
            searchesAreaModel.setArea(x);
            menuItems.add(searchesAreaModel);
            x++;
        }

        //Set the custom view
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getContext()).inflate(R.layout.searches_area_sheet_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.SearchesAreaSheetRecyclerView);

        searchesAreaSheetAdapter = new SearchesAreaSheetAdapter(menuItems, textSize, color, searchArea);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(searchesAreaSheetAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, int position) {

                dismiss();

                searchesAreaModel = menuItems.get(position);

                switch (position) {

                    case 0: // Law

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                    case 1: // Old Testament

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                    case 2: // Wisdom

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                    case 3: // Major Prophets

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                    case 4: // Minor Prophets

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                    case 5: // New Testament

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                    case 6: // Pauline letters

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                    case 7: // General letters

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                    case 8: // Revelation

                        preferenceProvider.setSearchArea(searchesAreaModel.getArea());

                        break;

                }

            }

            public void onLongClick(View view, int position) {
                //do nothing
            }
        }));

    }

}
