package org.armstrong.ika.digitalbibleapp.Main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import androidx.annotation.Nullable;

import static android.graphics.Typeface.BOLD_ITALIC;


public class MainNotice extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_notice, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
////        CoordinatorLayout.Behavior behavior = params.getBehavior();
////
////        if (behavior != null && behavior instanceof BottomSheetBehavior) {
////            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
////                @Override
////                public void onStateChanged(@NonNull View bottomSheet, int newState) {
////                    String state = "";
////
////                    switch (newState) {
////                        case BottomSheetBehavior.STATE_DRAGGING: {
////                            state = "DRAGGING";
////                            break;
////                        }
////                        case BottomSheetBehavior.STATE_SETTLING: {
////                            state = "SETTLING";
////                            break;
////                        }
////                        case BottomSheetBehavior.STATE_EXPANDED: {
////                            state = "EXPANDED";
////                            break;
////                        }
////                        case BottomSheetBehavior.STATE_COLLAPSED: {
////                            state = "COLLAPSED";
////                            break;
////                        }
////                        case BottomSheetBehavior.STATE_HIDDEN: {
////                            dismiss();
////                            state = "HIDDEN";
////                            break;
////                        }
////                    }
////
////                    //Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
////                }
////
////                @Override
////                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
////                }
////            });
////
////        }

        String supp = getString(R.string.active_version_warning);

        TextView alertText = view.findViewById(R.id.noticeText);
        alertText.setText(R.string.notice);
        alertText.setTextSize(preferenceProvider.getActionBarTextSizeVar());
        alertText.setTypeface(null, BOLD_ITALIC);
        alertText.setTextColor(preferenceProvider.getColorVar());

        TextView alertSuppText = view.findViewById(R.id.noticeSuppText);
        alertSuppText.setText(supp);
        alertSuppText.setTextSize(preferenceProvider.gettextSizeVar());

    }


}
