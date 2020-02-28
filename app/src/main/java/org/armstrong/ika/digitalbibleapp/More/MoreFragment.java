package org.armstrong.ika.digitalbibleapp.More;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import org.armstrong.ika.digitalbibleapp.About.AboutActivity;
import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.Completed.CompletedActivity;
import org.armstrong.ika.digitalbibleapp.Highlight.HighlightActivity;
import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.Notes.NotesActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.Version.VersionSheet;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends Fragment {

    private static MoreFragment instance;

    PreferenceProvider preferenceProvider;

    private RecyclerView recyclerView;
    private MoreFragmentAdapter moreFragmentAdapter;

    private List<MoreModel> moreItems = new ArrayList<MoreModel>();

    private MoreModel moreModel;

    private int currentBackgroundColor = 0xffffffff;

    protected ColorPickerView.WHEEL_TYPE wheelStyle;

    private String[] items;

    public static MoreFragment newInstance() {

        MoreFragment moreFragment = new MoreFragment();

        return moreFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        preferenceProvider = new PreferenceProvider(getContext());

        currentBackgroundColor = preferenceProvider.getColorVar();

        items = new String[]{
                getString(R.string.activate_versions),
                getString(R.string.highlights),
                getString(R.string.notes),
                getString(R.string.completed),
                getString(R.string.picker),
                getString(R.string.picker_style),
                getString(R.string.text_size),
                getString(R.string.share_app),
                getString(R.string.about)
        };

        for (String i : items) {
            moreModel = new MoreModel();
            moreModel.setMoreText(i);
            moreItems.add(moreModel);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.more_fragment, parent, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.MoreRecyclerView);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        moreFragmentAdapter = new MoreFragmentAdapter(moreItems, preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(moreFragmentAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                switch (position) {
                    case 0: // Activate/Deactivate Versions

                        final VersionSheet versionSheet = new VersionSheet();
                        versionSheet.show(getActivity().getSupportFragmentManager(), "versionSheet");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                versionSheet.dismiss();
                            }
                        }, 20000);

                        break;

                    case 1: // Highlights

                        Intent highlightActivity = new Intent(getActivity(), HighlightActivity.class);
                        startActivity(highlightActivity);
                        break;

                    case 2: // Notes

                        Intent notesActivity = new Intent(getActivity(), NotesActivity.class);
                        startActivity(notesActivity);
                        break;

                    case 3: // Completed

                        Intent completedActivity = new Intent(getActivity(), CompletedActivity.class);
                        startActivity(completedActivity);

                        break;

                    case 4: // color picker

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
                                .initialColor(currentBackgroundColor)
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

                                        changeCurrentBackgroundColor(selectedColor);

                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .showColorEdit(false)
                                //.setColorEditTextColor(ContextCompat.getColor(SampleActivity.this, android.R.color.holo_blue_bright))
                                .build()
                                .show();
                        break;

                    case 5: // color picker style

                        final MoreColorPickerSheet moreColorPickerSheet = new MoreColorPickerSheet();
                        moreColorPickerSheet.show(getActivity().getSupportFragmentManager(), "more color picker sheet");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                moreColorPickerSheet.dismiss();
                            }
                        }, 5000);

                        break;

                    case 6: // text size

                        final MoreSizeSheet moreSizeSheet = new MoreSizeSheet();
                        moreSizeSheet.show(getActivity().getSupportFragmentManager(), "moreSizeSheet");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                moreSizeSheet.dismiss();
                            }
                        }, 5000);

                        break;

                    case 7: // share Digital Bible App

                        StringBuilder sb = new StringBuilder();
                        sb.append(getString(R.string.share_dba_text));
                        sb.append("\n\n");
                        sb.append("https://play.google.com/store/apps/details?id=org.armstrong.ika.digitalbibleapp&amp;hl=en-GB&amp;ah=am-bre2MchrvZuoZauamIC3ceTk");

                        String subj = getString(R.string.app_name);

                        Intent shareIntent = new Intent();

                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subj);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());

                        Intent intent = Intent.createChooser(shareIntent, Integer.toString(R.string.share_app));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case 8: // about
                        Intent aboutActivity = new Intent(getActivity(), AboutActivity.class);
                        startActivity(aboutActivity);
                        break;
                }

            }

            // long click - unlock code
            public void onLongClick(View view, int position) {

                if (position == 0) {  // activate / deactivate versions

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    //builder.setTitle("Unlock");

                    View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.unlock, (ViewGroup) getView(), false);

                    final EditText input = viewInflated.findViewById(R.id.textInputEditText);

                    builder.setView(viewInflated);

                    builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                            String unlockInput = input.getText().toString();
                            unlockInput = Utils.makeMd5Hash(unlockInput);

                            if (Utils.checkMd5Hash(unlockInput)) {

                                // unlock
                                MainActivity.getInstance().unlockVersions();

                                Utils.makeToast(getContext(), getString(R.string.unlocked));

                            } else {

                                Utils.makeToast(getContext(), getString(R.string.error));
                            }

                        }
                    });
                    builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }

            }
        }));

    }

    public static MoreFragment getInstance() {
        return instance;
    }

    private void changeCurrentBackgroundColor(int selectedColor) {
        // set local variable
        currentBackgroundColor = selectedColor;
        // set Activity colors
        MoreActivity.getInstance().setBackgroundColors(selectedColor);

    }

    public void updateMoreRecyclerView(int size) {

        moreFragmentAdapter = new MoreFragmentAdapter(moreItems, size);
        recyclerView.setAdapter(moreFragmentAdapter);

    }

}
