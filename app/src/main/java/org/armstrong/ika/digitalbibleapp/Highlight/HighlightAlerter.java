package org.armstrong.ika.digitalbibleapp.Highlight;

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

import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.BOLD_ITALIC;


public class HighlightAlerter extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    private Button buttonYes;
    private Button buttonNo;

    String [] highlightRef;
    int[] highlightVars;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());
        highlightVars = preferenceProvider.getHighlightVars();
        highlightRef = preferenceProvider.getHighlightRef();
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
        alertText.setText(R.string.highlight_delete);
        alertText.setTextSize(preferenceProvider.getActionBarTextSizeVar());
        alertText.setTypeface(null, BOLD_ITALIC);
        alertText.setTextColor(preferenceProvider.getColorVar());

        TextView alertSuppText = view.findViewById(R.id.alertSuppText);
        alertSuppText.setText(highlightRef[0]);
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

                Utils.makeToast(getContext(), getString(R.string.highlight_deleted));

                HighlightFragment.getInstance().deleteHighlight(highlightVars[0]);
                HighlightFragment.getInstance().updateHighlightsRecyclerView(0);
            }
        });

    }


}
