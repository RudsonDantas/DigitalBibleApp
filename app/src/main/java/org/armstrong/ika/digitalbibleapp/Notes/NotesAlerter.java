package org.armstrong.ika.digitalbibleapp.Notes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.NotesDb.NotesRepository;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.BOLD_ITALIC;


public class NotesAlerter extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    NotesRepository notesRepository;

    private Button buttonYes;
    private Button buttonNo;

    String [] noteVars;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesRepository = new NotesRepository(getContext());

        preferenceProvider = new PreferenceProvider(getContext());

        noteVars = preferenceProvider.getNoteVars();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_alert, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        TextView alertText = view.findViewById(R.id.alertText);
        alertText.setText(R.string.note_delete);
        alertText.setTextSize(preferenceProvider.getActionBarTextSizeVar());
        alertText.setTypeface(null, BOLD_ITALIC);
        alertText.setTextColor(preferenceProvider.getColorVar());

        TextView alertSuppText = view.findViewById(R.id.alertSuppText);
        alertSuppText.setText(noteVars[1]);
        alertSuppText.setTextSize(preferenceProvider.gettextSizeVar());

        buttonNo = view.findViewById(R.id.button_no);
        buttonNo.setText(R.string.no);
        //buttonNo.setTextSize(Integer.parseInt(textSize));
        buttonNo.setTextColor(Color.BLACK);
        buttonNo.setTypeface(null, BOLD);

        buttonYes = view.findViewById(R.id.button_yes);
        buttonYes.setText(R.string.yes);
        //buttonYes.setTextSize(Integer.parseInt(textSize));
        buttonYes.setTextColor(Color.BLACK);
        buttonYes.setTypeface(null, BOLD);

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

               // Log.e("logg", "onClick delete id: " + noteVars[0]);

                if(notesRepository.deleteNote(Integer.parseInt(noteVars[0])) != -1) {

                    Utils.makeToast(getContext(), getString(R.string.note_deleted));
                }

            }
        });

    }


}
