package org.armstrong.ika.digitalbibleapp.Searches;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flask.colorpicker.ColorPickerView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkEntities;
import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkRepository;
import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.Common.Menu.MenuItemsAdapter;
import org.armstrong.ika.digitalbibleapp.Common.MenuItemsModel;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchesFragmentSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    private RecyclerView recyclerView;
    private MenuItemsAdapter menuItemsAdapter;

    private List<MenuItemsModel> menuItems = new ArrayList<>();

    private MenuItemsModel menuItemsModel;

    int b, c, v;
    private int textSize;

    protected ColorPickerView.WHEEL_TYPE wheelStyle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SearchesActivity.getInstance().searchView.clearFocus();

        preferenceProvider = new PreferenceProvider(getContext());

        textSize = preferenceProvider.gettextSizeVar();

        int[] searchVars = preferenceProvider.getSearchVars();

        b = searchVars[0];
        c = searchVars[1];
        v = searchVars[2];

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        String items[] = {
                getString(R.string.go_to),
                getString(R.string.bookmark),
                getString(R.string.share_verse)
        };

        for (String i : items) {
            menuItemsModel = new MenuItemsModel();
            menuItemsModel.setMenuItem(i);
            menuItems.add(menuItemsModel);
        }

        //Set the custom view
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.main_sheet_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.MainSheetFragmentRecyclerView);

        menuItemsAdapter = new MenuItemsAdapter(menuItems, textSize);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(menuItemsAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, int position) {

                String[] stringSearchVars = preferenceProvider.getStringSearchVars();
                int[] intSearchVars = preferenceProvider.getSearchVars();

                switch (position) {

                    case 0: // go to

                        dismiss();

                        int[] bookVars = {b, c, v};

                        preferenceProvider.setBookVars(bookVars);

                        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                        startActivity(mainActivity);


                        break;

                    case 1: // bookmark

                        dismiss();

                        Date date = new Date();

                        BookmarkRepository bookmarkRepository = new BookmarkRepository(getActivity());

                        BookmarkEntities bookmarkModel = new BookmarkEntities();

                        bookmarkModel.setDate(date.toString());
                        bookmarkModel.setLanguage(preferenceProvider.getLang());
                        bookmarkModel.setAbbreviation(stringSearchVars[0]);
                        bookmarkModel.setText(stringSearchVars[1]);
                        bookmarkModel.setBook_name(stringSearchVars[2]);

                        bookmarkModel.setVersion(preferenceProvider.getVersion());
                        bookmarkModel.setBook(intSearchVars[0]);
                        bookmarkModel.setChapter(intSearchVars[1]);
                        bookmarkModel.setVerse(intSearchVars[2]);

                        if (bookmarkRepository.insertBookmark(bookmarkModel) != -1) {

                            Utils.makeToast(getContext(), getString(R.string.bookmark_added));

                            preferenceProvider.setBookmarkCount(bookmarkRepository.countBookMarks());
                        }

                        break;

//                    case 2: // Highlight
//
//                        dismiss();
//
//                        HighlightRepository highlightRepository;
//                        highlightRepository = new HighlightRepository(getContext());
//
//                        final HighlightEntities highlightEntities = new HighlightEntities();
//
//                        switch (preferenceProvider.getWheelStyle()) {
//                            case "0":
//                                wheelStyle = ColorPickerView.WHEEL_TYPE.FLOWER;
//                                break;
//                            case "1":
//                                wheelStyle = ColorPickerView.WHEEL_TYPE.CIRCLE;
//                                break;
//                        }
//
//                        ColorPickerDialogBuilder
//                                .with(getContext(), R.style.Theme_AppCompat_Dialog_Alert)
//                                //.setTitle(R.string.color_dialog_title)
//                                .initialColor(0xffffffff)
//                                .wheelType(wheelStyle)
//                                .density(12)
//                                .setOnColorChangedListener(new OnColorChangedListener() {
//                                    @Override
//                                    public void onColorChanged(int selectedColor) {
//                                        // Handle on color change
//                                        //toast("onColorChanged: 0x" + Integer.toHexString(selectedColor));
//                                    }
//                                })
//                                .setOnColorSelectedListener(new OnColorSelectedListener() {
//                                    @Override
//                                    public void onColorSelected(int selectedColor) {
//                                        //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
//                                        //Utils.makeToast(getContext(),"onColorSelected: 0x" + Integer.toHexString(selectedColor));
//                                    }
//                                })
//                                .setPositiveButton(R.string.ok, new ColorPickerClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//
//                                        int z = preferenceProvider.getVersion(); // version
//                                        int b = intSearchVars[0]; // book
//                                        int c = intSearchVars[1]; // chapter
//                                        int v = intSearchVars[2]; // verse
//                                        //int p = positionVars[0]; // position
//
//                                        // color selection
//                                        int col = 0;
//                                        if (!(selectedColor == -1)) { // a color is selected
//                                            col = lighter(selectedColor, 0.6f);
//                                        } else { // no colour selected, use light gray
//                                            col = lighter(-3355444, 0.6f); // LTGray
//                                        }
//
//                                        // check if entry exists in highlight database
//                                        int hid = highlightRepository.getHighlightExists(z, b, c, v);
//
//                                        if (hid != 0) {
//                                            // if entry already exists, update color
//                                            highlightRepository.updateColor(col, hid);
//
//                                        } else {
//
//                                            // if entry does not exist, insert new entry
//                                            highlightEntities.setAbbreviation(stringSearchVars[0]);
//                                            highlightEntities.setVersion(z);
//                                            highlightEntities.setBookname(stringSearchVars[2]);
//                                            highlightEntities.setBook(b);
//                                            highlightEntities.setChapter(c);
//                                            highlightEntities.setVerse(v);
//                                            highlightEntities.setText(stringSearchVars[1]);
//                                            highlightEntities.setColor(col);
//
//                                            if (highlightRepository.insertHighlight(highlightEntities) != -1) { // returns long
//                                                // toast gives error
//                                                //Utils.makeToast(getContext(),getString(R.string.highlight_added));
//
//                                            }
//                                        }
//                                        // refresh search list
//                                        SearchesFragment.getInstance().reloadSearch();
//
//
//                                    }
//                                })
//                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // do nothing
//                                    }
//                                })
//                                .showColorEdit(false)
//                                //.setColorEditTextColor(ContextCompat.getColor(SampleActivity.this, android.R.color.holo_blue_bright))
//                                .build()
//                                .show();
//
//                        break;

//                    case 3: // Notes
//
//                        dismiss();
//
//                        StringBuilder sb = new StringBuilder();
//
//                        sb.append(stringSearchVars[2]);
//                        sb.append(" ");
//                        sb.append(intSearchVars[1]);
//                        sb.append(":");
//                        sb.append(intSearchVars[2]);
//
//                        String noteText = stringSearchVars[1];
//
//                        String[] noteVars = {"", sb.toString(), noteText};
//
//                        preferenceProvider.setNoteVars(noteVars);
//
//                        Intent noteIntent = new Intent(getActivity(), NoteActivity.class);
//                        noteIntent.putExtra("returnTo", "SearchesActivity");
//                        noteIntent.putExtra("action", "insert");
//                        startActivity(noteIntent);
//
//                        break;

                    case 2: // share

                        dismiss();

                        String subj = getString(R.string.app_name);

                        StringBuilder ssb = new StringBuilder();

                        ssb.append(stringSearchVars[0]);
                        ssb.append(" ");
                        ssb.append(stringSearchVars[2]);
                        ssb.append(" ");
                        ssb.append(intSearchVars[1]);
                        ssb.append(":");
                        ssb.append(intSearchVars[2]);
                        ssb.append("\n");
                        ssb.append(stringSearchVars[1]);

                        Intent shareIntent = new Intent();

                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subj);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, ssb.toString());

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


}
