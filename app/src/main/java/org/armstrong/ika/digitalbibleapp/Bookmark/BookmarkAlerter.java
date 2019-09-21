package org.armstrong.ika.digitalbibleapp.Bookmark;

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

import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkRepository;

import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.BOLD_ITALIC;


public class BookmarkAlerter extends BottomSheetDialogFragment {

    protected PreferenceProvider preferenceProvider;

    protected BookmarkRepository bookmarkRepository;

    private Button buttonYes;
    private Button buttonNo;

    int bookmarkId;
    String[] bookmarkRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookmarkRepository = new BookmarkRepository(getContext());

        preferenceProvider = new PreferenceProvider(getContext());
        bookmarkId = preferenceProvider.getBookmarkId();
        bookmarkRef = preferenceProvider.getBookmarkRef();

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
        alertText.setText(R.string.delete_bookmark);
        alertText.setTextSize(preferenceProvider.gettextSizeVar());
        alertText.setTypeface(null, BOLD_ITALIC);
        alertText.setTextColor(preferenceProvider.getColorVar());

        TextView alertSuppText = view.findViewById(R.id.alertSuppText);
        alertSuppText.setText(bookmarkRef[0]);
        alertSuppText.setTextSize(preferenceProvider.gettextSizeVar());

        buttonNo = view.findViewById(R.id.button_no);
        buttonNo.setText(getText(R.string.no));
        //buttonNo.setTextSize(Integer.parseInt(textSize));
        buttonNo.setTextColor(Color.BLACK);
        buttonNo.setTypeface(null, BOLD);

        buttonYes = view.findViewById(R.id.button_yes);
        buttonYes.setText(getText(R.string.yes));
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

                if(bookmarkRepository.deleteBookmark(bookmarkId) == 1){

                    Utils.makeToast(getContext(),getString(R.string.bookmark_deleted));

                    preferenceProvider.setBookmarkCount(bookmarkRepository.countBookMarks());

                    BookmarkActivity.getInstance().bookMarkNotification();
                }


            }
        });

    }


}
