package org.armstrong.ika.digitalbibleapp.Notes;

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

import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;

import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesSheet extends BottomSheetDialogFragment {

    protected PreferenceProvider preferenceProvider;

    protected RecyclerView recyclerView;

    protected NotesSheetAdapter notesSheetAdapter;

    private List<NotesSheetModel> menuItems = new ArrayList<>();

    protected NotesSheetModel notesSheetModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        String items[] = {getString(R.string.edit), getString(R.string.delete)};

        for (String i : items) {
            notesSheetModel = new NotesSheetModel();
            notesSheetModel.setNotesSheetMenuText(i);
            menuItems.add(notesSheetModel);
        }

        //Set the custom view
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.notes_sheet_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.NotesSheetRecyclerView);

        notesSheetAdapter = new NotesSheetAdapter(menuItems, preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(notesSheetAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, int position) {

                switch (position) {

                    case 0: // edit

                        dismiss();

                        // go to bookmarked text
                        Intent noteActivity = new Intent(getActivity(), NoteListActivity.class);
                        noteActivity.putExtra("action", "edit");
                        startActivity(noteActivity);


                        break;

                    case 1: // delete

                        dismiss();

                        final NotesAlerter notesAlerter = new NotesAlerter();
                        notesAlerter.show(getActivity().getSupportFragmentManager(), "notesAlerter");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notesAlerter.dismiss();
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
