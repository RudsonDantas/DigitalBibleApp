package org.armstrong.ika.digitalbibleapp.Main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkEntities;
import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkRepository;
import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;

import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.Compare.CompareSheet;
import org.armstrong.ika.digitalbibleapp.Completed.CompletedSheet;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightRepository;
import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.Notes.NoteActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.armstrong.ika.digitalbibleapp.Common.Utils.lighter;

public class MainSheet extends BottomSheetDialogFragment {

    protected PreferenceProvider preferenceProvider;

    protected BookmarkRepository bookmarkRepository;

    private RecyclerView recyclerView;
    private MainSheetAdapter mainSheetAdapter;

    protected VersionRepository versionRepository;

    protected HighlightRepository highlightRepository;

    private List<MainSheetModel> menuItems = new ArrayList<>();

    private MainSheetModel mainSheetModel;

    protected ColorPickerView.WHEEL_TYPE wheelStyle;
    protected Utils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        versionRepository = new VersionRepository(getContext());

        bookmarkRepository = new BookmarkRepository(getContext());

        preferenceProvider = new PreferenceProvider(getContext());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        String items[] = {
                getString(R.string.compare),
                getString(R.string.bookmark),
                getString(R.string.highlight),
                getString(R.string.add_note),
                getString(R.string.completed),
                getString(R.string.share_verse)
        };

        for (String i : items) {
            mainSheetModel = new MainSheetModel();
            mainSheetModel.setMenuItem(i);
            menuItems.add(mainSheetModel);
        }

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.main_sheet_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.MainSheetFragmentRecyclerView);

        mainSheetAdapter = new MainSheetAdapter(menuItems, preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mainSheetAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, final int position) {

                final int[] versionVars = preferenceProvider.getVersionVars();
                final int[] positionVars = preferenceProvider.getPositionVars();
                final String[] bookMarkVars = preferenceProvider.getBookmarkVars();

                switch (position) {

                    case 0: // compare

                        dismiss();

                        int av_count = versionRepository.countActiveVersions();

                        if (av_count < 2) {

                            activeVersionsWarning();

                        } else {

                            CompareSheet compareSheet = new CompareSheet();
                            compareSheet.show(getActivity().getSupportFragmentManager(), "compareSheet");

                        }

                        break;


                    case 1: // bookmark

                        dismiss();

                        BookmarkEntities bookmarkEntities = new BookmarkEntities();

                        bookmarkEntities.setDate(bookMarkVars[0]);
                        bookmarkEntities.setLanguage(bookMarkVars[1]);
                        bookmarkEntities.setAbbreviation(bookMarkVars[2]);
                        bookmarkEntities.setVersion(versionVars[0]);
                        bookmarkEntities.setBook(versionVars[1]);
                        bookmarkEntities.setChapter(versionVars[2]);
                        bookmarkEntities.setVerse(versionVars[3]);
                        bookmarkEntities.setText(bookMarkVars[3]);
                        bookmarkEntities.setBook_name(bookMarkVars[4]);

                        if (bookmarkRepository.insertBookmark(bookmarkEntities) != -1) {

                            Utils.makeToast(getContext(), getString(R.string.bookmark_added));

                            preferenceProvider.setBookmarkCount(bookmarkRepository.countBookMarks());

                            MainActivity.getInstance().bookMarkNotification();
                        }


                        break;

                    case 2: // highlight

                        dismiss();

                        highlightRepository = new HighlightRepository(getContext());

                        final HighlightEntities highlightEntities = new HighlightEntities();

                        switch (preferenceProvider.getWheelStyle()) {
                            case "0":
                                wheelStyle = ColorPickerView.WHEEL_TYPE.FLOWER;
                                break;
                            case "1":
                                wheelStyle = ColorPickerView.WHEEL_TYPE.CIRCLE;
                                break;
                        }

                        ColorPickerDialogBuilder
                                .with(getContext(), R.style.Theme_AppCompat_Dialog_Alert)
                                //.setTitle(R.string.color_dialog_title)
                                .initialColor(0xffffffff)
                                .wheelType(wheelStyle)
                                .density(12)
                                .setOnColorChangedListener(new OnColorChangedListener() {
                                    @Override
                                    public void onColorChanged(int selectedColor) {
                                        // Handle on color change
                                        //toast("onColorChanged: 0x" + Integer.toHexString(selectedColor));
                                    }
                                })
                                .setOnColorSelectedListener(new OnColorSelectedListener() {
                                    @Override
                                    public void onColorSelected(int selectedColor) {
                                        //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                                        //Utils.makeToast(getContext(),"onColorSelected: 0x" + Integer.toHexString(selectedColor));
                                    }
                                })
                                .setPositiveButton(R.string.ok, new ColorPickerClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                                        int z = versionVars[0];
                                        int b = versionVars[1];
                                        int c = versionVars[2];
                                        int v = versionVars[3];
                                        int p = positionVars[0]; // position

                                        // color selection
                                        int col = 0;
                                        if (!(selectedColor == -1)) { // a color is selected
                                            col = lighter(selectedColor, 0.6f);
                                        } else { // no colour selected, use light gray
                                            col = lighter(-3355444, 0.6f); // LTGray
                                        }

                                        // check if entry exists in highlight database
                                        int hid = highlightRepository.getHighlightExists(z, b, c, v);

                                        if (hid != 0) {
                                            // if entry already exists, update color
                                            highlightRepository.updateColor(col, hid);

                                        } else {

                                            // if entry does not exist, insert new entry
                                            highlightEntities.setAbbreviation(bookMarkVars[2]);
                                            highlightEntities.setVersion(z);
                                            highlightEntities.setBookname(bookMarkVars[4]);
                                            highlightEntities.setBook(b);
                                            highlightEntities.setChapter(c);
                                            highlightEntities.setVerse(v);
                                            highlightEntities.setText(bookMarkVars[3]);
                                            highlightEntities.setColor(col);

                                            if (highlightRepository.insertHighlight(highlightEntities) != -1) { // returns long
                                                // toast gives an error
                                                //utils.makeToast(getContext(),getString(R.string.highlight_added));

                                            }
                                        }
                                        // refresh view pages
                                        MainActivity.getInstance().refreshViewPager(p);


                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .showColorEdit(false)
                                //.setColorEditTextColor(ContextCompat.getColor(SampleActivity.this, android.R.color.holo_blue_bright))
                                .build()
                                .show();

                        break;

                    case 3: // note

                        dismiss();

                        StringBuilder nsb = new StringBuilder();

                        nsb.append(bookMarkVars[2]); // version abbreviation
                        nsb.append(" ");
                        nsb.append(bookMarkVars[4]); // book name
                        nsb.append(" ");
                        nsb.append(versionVars[2]); // chapter
                        nsb.append(":");
                        nsb.append(versionVars[3]); // verse

                        String[] StringVars = {"", nsb.toString(), bookMarkVars[3]}; // id, ref, text

                        // version, book, chapter, verse
                        int[] IntItems = {versionVars[0],versionVars[1],versionVars[2],versionVars[3]};

                        preferenceProvider.setNoteVars(StringVars);
                        preferenceProvider.setNoteIntVars(IntItems);

                        Intent noteIntent = new Intent(getActivity(), NoteActivity.class);
                        noteIntent.putExtra("returnTo", "MainActivity");
                        noteIntent.putExtra("action", "get");
                        startActivity(noteIntent);

                        break;

                    case 4: // completed

                        dismiss();

                        CompletedSheet completedSheet = new CompletedSheet();
                        completedSheet.show(getActivity().getSupportFragmentManager(), "completedSheet");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                completedSheet.dismiss();
                            }
                        }, 5000);

                        break;

                    case 5: // share

                        dismiss();

                        StringBuilder sb = new StringBuilder();

                        sb.append(bookMarkVars[2]);
                        sb.append(" ");
                        sb.append(bookMarkVars[4]);
                        sb.append(" ");
                        sb.append(versionVars[2]);
                        sb.append(":");
                        sb.append(versionVars[3]);
                        sb.append("\n");
                        sb.append(bookMarkVars[3]);

                        Intent shareIntent = new Intent();

                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.setType("text/plain");
                        //shareIntent.putExtra(Intent.EXTRA_SUBJECT, subj);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());

                        Intent intent = Intent.createChooser(shareIntent, getString(R.string.share_verse));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        break;
                }

            }

            public void onLongClick(View view, int position) {
                //do nothing
            }

        }));

    }

    public void activeVersionsWarning() {

        final MainNotice mainNotice = new MainNotice();
        mainNotice.show(getActivity().getSupportFragmentManager(), "mainNotice");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainNotice.dismiss();
            }
        }, 5000);

    }


}
