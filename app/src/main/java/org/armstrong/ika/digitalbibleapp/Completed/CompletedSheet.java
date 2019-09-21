package org.armstrong.ika.digitalbibleapp.Completed;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.CompletedDb.CompletedEntities;
import org.armstrong.ika.digitalbibleapp.CompletedDb.CompletedRepository;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.BOLD_ITALIC;


public class CompletedSheet extends BottomSheetDialogFragment {

    protected PreferenceProvider preferenceProvider;
    protected CompletedRepository completedRepository;

    private Button buttonYes;
    private Button buttonNo;

    String[] bookMarkVars;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        completedRepository = new CompletedRepository(getContext());

        preferenceProvider = new PreferenceProvider(getContext());

        bookMarkVars = preferenceProvider.getBookmarkVars();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.completed_alert, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        TextView alertText = view.findViewById(R.id.alertText);
        alertText.setText(R.string.completed_mark);
        alertText.setTextSize(preferenceProvider.gettextSizeVar());
        alertText.setTypeface(null, BOLD_ITALIC);
        alertText.setTextColor(preferenceProvider.getColorVar());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bookMarkVars[2]);
        stringBuilder.append(" ");
        stringBuilder.append(bookMarkVars[4]);

        TextView alertSuppText = view.findViewById(R.id.alertSuppText);
        alertSuppText.setText(stringBuilder.toString());
        alertSuppText.setTextSize(preferenceProvider.gettextSizeVar());

        buttonNo = view.findViewById(R.id.button_no);
        buttonNo.setText(getText(R.string.no));
        buttonNo.setTextColor(Color.BLACK);
        buttonNo.setTypeface(null, BOLD);

        buttonYes = view.findViewById(R.id.button_yes);
        buttonYes.setText(getText(R.string.yes));
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

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String ts = sdf.format(timestamp);

                CompletedEntities completedEntities = new CompletedEntities();

                completedEntities.setAbbreviation(bookMarkVars[2]);
                completedEntities.setBookname(bookMarkVars[4]);
                completedEntities.setTime(ts);

                if (completedRepository.insertCompleted(completedEntities) != -1) {

                    Utils.makeToast(getContext(), getString(R.string.completed_marked));

                }

            }

        });

    }


}
