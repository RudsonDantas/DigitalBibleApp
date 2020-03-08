package org.armstrong.ika.digitalbibleapp.Notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.NotesDb.NotesEntities;
import org.armstrong.ika.digitalbibleapp.NotesDb.NotesRepository;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.Date;
import java.util.List;

public class NoteFragment extends Fragment {

    PreferenceProvider preferenceProvider;

    protected NotesRepository notesRepository;

    View view;

    EditText ref;
    EditText note;

    private String action;
    private String noteID;
    private int textSize;

    TextInputLayout textRefInputLayout, textNoteInputLayout;

    private Button updateBtn, deleteBtn;

    public static NoteFragment newInstance(String action) {

        NoteFragment notesFragment = new NoteFragment();

        Bundle args = new Bundle();
        args.putString("action", action);
        notesFragment.setArguments(args);

        return notesFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());
        textSize = preferenceProvider.gettextSizeVar();

        notesRepository = new NotesRepository(getContext());

        action = getArguments().getString("action", "new");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.note_fragment, parent, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ref = view.findViewById(R.id.ref);
        note = view.findViewById(R.id.note);

        switch (action) {

            case "new":

                ref.setText("");
                note.setText("");

                break;

            case "edit":

                String[] editNoteVars = preferenceProvider.getNoteVars();

                noteID = editNoteVars[0];
                ref.setText(editNoteVars[1]);
                note.setText(editNoteVars[2]);

                break;

            case "get":

                Integer[] noteIntVars = preferenceProvider.getNoteIntVars();

                List<NotesEntities> notesEntities = notesRepository.getNoteAllRefs(noteIntVars[0], noteIntVars[1], noteIntVars[2], noteIntVars[3]);

                if (notesEntities.size() > 0) { // retrieve if already exists

                    action = "edit";

                    noteID = String.valueOf(notesEntities.get(0).getId());
                    ref.setText(notesEntities.get(0).getRef());
                    note.setText(notesEntities.get(0).getText());

                } else {

                    action = "new";

                    String[] getNoteVars = preferenceProvider.getNoteVars();

                    ref.setText(getNoteVars[1]);
                    note.setText(getNoteVars[2]);

                }

                break;
        }

        ref.setTextSize(textSize);
        note.setTextSize(textSize);

        textRefInputLayout = view.findViewById(R.id.textRefInputLayout);
        textNoteInputLayout = view.findViewById(R.id.textNoteInputLayout);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Date date = new Date();
        String dat = date.toString();

        updateBtn = view.findViewById(R.id.btn_update);
        deleteBtn = view.findViewById(R.id.btn_delete);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (action) {

                    case "new":

                        Integer[] noteIntVars = preferenceProvider.getNoteIntVars();

                        NotesEntities notesEntities = new NotesEntities();
                        notesEntities.setDate(dat);
                        notesEntities.setRef(Utils.escapeString(ref.getText().toString()));
                        notesEntities.setText(Utils.escapeString(note.getText().toString()));
                        notesEntities.setVersion(noteIntVars[0]);  // version
                        notesEntities.setBook(noteIntVars[1]);  // book
                        notesEntities.setChapter(noteIntVars[2]); // chapter
                        notesEntities.setVerse(noteIntVars[3]); // verse

                        if (notesRepository.insertNote(notesEntities) != -1) {

                            Utils.makeToast(getContext(), getString(R.string.note_inserted));

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent returnIntent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(returnIntent);
                                }
                            }, 1000);
                        }
                        break;

                    case "edit":

                        String reff = Utils.escapeString(ref.getText().toString());
                        String txtt = Utils.escapeString(note.getText().toString());

                        if (notesRepository.updateNote(dat, reff, txtt, Integer.parseInt(noteID)) != -1) {

                            Utils.makeToast(getContext(), getString(R.string.note_updated));

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent returnIntent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(returnIntent);
                                }
                            }, 1000);
                        }
                        break;
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.are_you_sure));

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle(R.string.delete)
                        .setMessage(sb.toString())
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialoginterface, int i) {

                                if (noteID != null) { // delete note with id

                                    if (notesRepository.deleteNote(Integer.parseInt(noteID)) != -1) {

                                        Utils.makeToast(getContext(), getString(R.string.note_deleted));

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent returnIntent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(returnIntent);
                                            }
                                        }, 1000);
                                    }

                                } else { // no id, just return

                                    Utils.makeToast(getContext(), getString(R.string.note_deleted));

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent returnIntent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(returnIntent);
                                        }
                                    }, 1000);

                                }
                            }
                        }).show();
            }
        });

        ref.addTextChangedListener(new TextWatcher() { // ref length
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > textRefInputLayout.getCounterMaxLength()) {
                    textRefInputLayout.setError(getString(R.string.max_ref) + " "
                            + textRefInputLayout.getCounterMaxLength());
                } else {
                    textRefInputLayout.setError(null);
                }

            }
        });

        note.addTextChangedListener(new TextWatcher() { // note length
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > textNoteInputLayout.getCounterMaxLength()) {
                    textNoteInputLayout.setError(getString(R.string.max_note) + " "
                            + textNoteInputLayout.getCounterMaxLength());
                } else {
                    textNoteInputLayout.setError(null);
                }

            }
        });

    }


}
