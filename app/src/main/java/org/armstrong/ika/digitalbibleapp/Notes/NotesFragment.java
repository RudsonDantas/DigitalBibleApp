package org.armstrong.ika.digitalbibleapp.Notes;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;

import org.armstrong.ika.digitalbibleapp.NotesDb.NotesEntities;
import org.armstrong.ika.digitalbibleapp.NotesDb.NotesViewModel;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesFragment extends Fragment {

    PreferenceProvider preferenceProvider;

    protected NotesViewModel notesViewModel;

    private RecyclerView recyclerView;
    private NotesFragmentAdapter notesFragmentAdapter;

    private List<NotesEntities> notes;

    public static NotesFragment newInstance() {

        NotesFragment notesFragment = new NotesFragment();

        return notesFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.notes_fragment, parent, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.NotesRecyclerView);

        notesFragmentAdapter = new NotesFragmentAdapter(preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(notesFragmentAdapter);

        notesViewModel = ViewModelProviders.of(getActivity()).get(NotesViewModel.class);

        notesViewModel.getAllNotes().observe(getActivity(), new Observer<List<NotesEntities>>() {
            @Override
            public void onChanged(List<NotesEntities> notesEntities) {
                notesFragmentAdapter.setNotes(notesEntities);
                notes = notesEntities;
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                NotesEntities notesEntities = notes.get(position);

                String [] noteVars = {
                        Integer.toString(notesEntities.getId()),
                        notesEntities.getRef(),
                        notesEntities.getText()
                };

                // save note
                preferenceProvider.setNoteVars(noteVars);

                final NotesSheet notesSheet = new NotesSheet();
                notesSheet.show(getActivity().getSupportFragmentManager(), "notesSheet");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notesSheet.dismiss();
                    }
                }, 5000);

            }

            // long click
            public void onLongClick(View view, int position) {

                // do nothing

            }

        }));

    }


}
