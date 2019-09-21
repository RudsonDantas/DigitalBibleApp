package org.armstrong.ika.digitalbibleapp.Reference;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesRepository;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReferenceFragThree extends Fragment {

    private static ReferenceFragThree instance;

    protected PreferenceProvider preferenceProvider;

    protected BiblesRepository biblesRepository;

    private RecyclerView recyclerView;
    private ReferenceFragThreeAdapter referenceFragThreeAdapter;

    int[] values;

    private int book;
    private int chapter;
    private int textSize;

    public static ReferenceFragThree newInstance() {
        ReferenceFragThree referenceFragOne = new ReferenceFragThree();
        return referenceFragOne;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        preferenceProvider = new PreferenceProvider(getContext());

        int [] versionVars = preferenceProvider.getVersionVars();

        textSize = preferenceProvider.gettextSizeVar();

        biblesRepository = new BiblesRepository(getContext());
        biblesRepository.initialize(versionVars[0]);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.reference_fragthree, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        int numberOfColumns = 6;

        recyclerView = view.findViewById(R.id.ReferenceFragRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateVerseGrid();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                int verse = values[position];

                String title = ReferenceFragOne.getInstance().book_name
                        + " "
                        + ReferenceFragTwo.getInstance().chapter
                        + ":"
                        + verse;

                ReferenceActivity.getInstance().textOne.setText(title);

                preferenceProvider.setVerse(verse);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);

                    }
                }, 200);

            }

            // long click
            public void onLongClick(View view, int position) {
                // do nothing
            }

        }));
    }

    public void updateVerseGrid() {

        int [] bookVars = preferenceProvider.getBookVars();

        book = bookVars[0];
        chapter = bookVars[1];

        int cnt = biblesRepository.getVerseCount(book, chapter);

        values = new int[cnt];
        for (int i = 0; i < cnt; ++i) {
            values[i] = i + 1;
        }

        referenceFragThreeAdapter = new ReferenceFragThreeAdapter(values, textSize);
        recyclerView.setAdapter(referenceFragThreeAdapter);

    }

    public static ReferenceFragThree getInstance() {
        return instance;
    }

}
