package org.armstrong.ika.digitalbibleapp.Highlight;

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

import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;

import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.Common.Utils;

import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightRepository;
import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

import java.util.ArrayList;
import java.util.List;

public class HighlightSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    protected VersionRepository versionRepository;
    protected HighlightRepository highlightRepository;

    private RecyclerView recyclerView;
    private HighlightSheetAdapter highlightSheetAdapter;

    private List<HighlightEntities> menuItems = new ArrayList<>();
    private HighlightEntities highlightEntities;

    protected ColorPickerView.WHEEL_TYPE wheelStyle;

    int[] highlightVars;
    int version;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());
        highlightVars = preferenceProvider.getHighlightVars();
        version = preferenceProvider.getVersion();

        versionRepository = new VersionRepository(getContext());

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        highlightRepository = new HighlightRepository(getContext());

        String menu[] = {
                getString(R.string.go_to),
                getString(R.string.change_color),
                getString(R.string.delete)
        };

        for (String i : menu) {
            highlightEntities = new HighlightEntities();
            highlightEntities.setText(i);
            menuItems.add(highlightEntities);
        }

        //Set the custom view
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.highlights_sheet_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        recyclerView = view.findViewById(R.id.highlightsSheetRecyclerView);

        highlightSheetAdapter = new HighlightSheetAdapter(menuItems, preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(highlightSheetAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, int position) {

                switch (position) {

                    case 0: // go to

                        dismiss();

                        // make version active
                        versionRepository.setVersionActive(1, version);// active, version

                        int[] IntItems = {
                                highlightVars[1],
                                highlightVars[2],
                                highlightVars[3],
                                highlightVars[4]
                        };

                        preferenceProvider.setVersionVars(IntItems);

                        // go to highlighted text
                        final Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);


                        break;

                    case 1: // change colour

                        dismiss();

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

                                        int id = highlightVars[0];
                                        int pos = highlightVars[5];

                                        int col = 0;
                                        if (!(selectedColor == -1)) { // a color is selected
                                            col = Utils.lighter(selectedColor, 0.6f);
                                        } else { // else use light gray
                                            col = Utils.lighter(-3355444, 0.6f); // LTGray
                                        }

                                        // update highlight color
                                        if (highlightRepository.updateColor(col, id) > 0) {
                                            // reload list
                                            HighlightFragment.getInstance().updateHighlightsRecyclerView(pos);
                                        }

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

                    case 2: // delete

                        dismiss();

                        final HighlightAlerter highlightAlerter = new HighlightAlerter();
                        highlightAlerter.show(getActivity().getSupportFragmentManager(), "highlightAlerter");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                highlightAlerter.dismiss();
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
