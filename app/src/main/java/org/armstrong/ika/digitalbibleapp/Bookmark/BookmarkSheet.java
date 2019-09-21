package org.armstrong.ika.digitalbibleapp.Bookmark;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;

import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BookmarkSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    protected VersionRepository versionRepository;

    private RecyclerView recyclerView;
    private BookmarkSheetAdapter bookmarkSheetAdapter;

    private List<BookmarkSheetModel> menuItems = new ArrayList<>();
    private BookmarkSheetModel bookmarkSheetModel;

    int version;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());
        version = preferenceProvider.getVersion();

        versionRepository = new VersionRepository(getContext());

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        String items[] = {getString(R.string.go_to), getString(R.string.delete)};

        for (String i : items) {
            bookmarkSheetModel = new BookmarkSheetModel();
            bookmarkSheetModel.setMenuText(i);
            menuItems.add(bookmarkSheetModel);
        }

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_sheet_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.BookmakrSheetRecyclerView);

        bookmarkSheetAdapter = new BookmarkSheetAdapter(menuItems, preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(bookmarkSheetAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, int position) {

                switch (position) {
                    case 0: // go to bookmarked text

                        dismiss();

                        // make version active
                        versionRepository.setVersionActive(1, version); // active, version

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);


                        break;
                    case 1: // delete bookmarked text

                        dismiss();

                        final BookmarkAlerter bookmarkAlerter = new BookmarkAlerter();
                        bookmarkAlerter.show(getActivity().getSupportFragmentManager(), "bookmarkAlerter");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bookmarkAlerter.dismiss();
                            }
                        }, 5000);

                        break;
                }

            }

            public void onLongClick(View view, int position) {
                //do nothing
            }
        }));


    }


}
