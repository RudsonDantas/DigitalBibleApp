package org.armstrong.ika.digitalbibleapp.Main;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesEntities;
//import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesRepository;

import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesViewModel;
import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesViewModelFactory;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightRepository;
import org.armstrong.ika.digitalbibleapp.NotesDb.NotesEntities;
import org.armstrong.ika.digitalbibleapp.NotesDb.NotesRepository;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {

    private static MainFragment instance;

    protected PreferenceProvider preferenceProvider;

    private int version;
    private int book;
    private int chapter;
    public int verse;

    private String abbreviation;
    private String lang;
    private String book_name;

    private int textSize;

    public RecyclerView recyclerView;
    private MainFragmentAdapter recyclerViewAdapter;

    //private CustomLinearLayoutManager customLinearLayoutManager;
    //protected LinearLayoutManager linearLayoutManager;

    //protected BiblesRepository biblesRepository;

    protected BiblesViewModel biblesViewModel;

    protected VersionRepository versionRepository;

    private View view;

    private List<BiblesEntities> values;
    private BiblesEntities biblesEntities;

    protected HighlightRepository highlightRepository;
    protected NotesRepository notesRepository;

    private List<HighlightEntities> highlights = new ArrayList<>();
    private List<NotesEntities> notesEntities = new ArrayList<>();

    private int[] versionVars;

    public int target;


    public static MainFragment newInstance(
            int chapter,
            int verse,
            int book,
            String abbreviation,
            String lang,
            String book_name,
            int version
    ) {

        MainFragment mainFragment = new MainFragment();

        Bundle args = new Bundle();
        args.putInt("book", book);
        args.putInt("chapter", chapter);
        args.putInt("verse", verse);
        args.putString("abbreviation", abbreviation);
        args.putString("lang", lang);
        args.putString("book_name", book_name);
        args.putInt("version", version);
        mainFragment.setArguments(args);

        return mainFragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        preferenceProvider = new PreferenceProvider(getContext());
        versionVars = preferenceProvider.getVersionVars();

        versionRepository = new VersionRepository(getContext());

        //biblesRepository = new BiblesRepository(getContext());
        //biblesRepository.initialize(versionVars[0]);

        highlightRepository = new HighlightRepository(getContext());
        notesRepository = new NotesRepository(getContext());

        book = versionVars[1];
        chapter = getArguments().getInt("chapter", 1);
        verse = getArguments().getInt("verse", 1);
        abbreviation =  getArguments().getString("abbreviation", "KJV");
        lang = getArguments().getString("lang", "eng");
        book_name =  getArguments().getString("book_name", "John");
        version = getArguments().getInt("version", 1);
        textSize = preferenceProvider.gettextSizeVar();

        // update active versions - necessary because of DB change.
        versionRepository.setVersionActive(1, book);

        target = verse - 1;

        chapter = chapter + 1;  // make vpager chapter match DB chapter

        highlights = highlightRepository.getChapHighlights(version, book, chapter);

        notesEntities = notesRepository.getNoteReferences(version,book,chapter);

        biblesViewModel = ViewModelProviders.of(getActivity(),
                new BiblesViewModelFactory(new Application(), version)).get(BiblesViewModel.class);

        //values = biblesRepository.getBookAndChapter(book,chapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.MainRecyclerView);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewAdapter = new MainFragmentAdapter(highlights, notesEntities, textSize);

        //CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

        biblesViewModel.getBookChapter(book, chapter).observe(getActivity(),
                new Observer<List<BiblesEntities>>() {
                    @Override
                    public void onChanged(List<BiblesEntities> biblesEntities) {
                        recyclerViewAdapter.setValues(biblesEntities);
                        recyclerView.scrollToPosition(target);

                        values = biblesEntities;
                    }
                });

        //recyclerViewAdapter.setValues(values);

        //customLinearLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), target);
        //scrollToTarget(verse - 1);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {


            public void onClick(View view, int position) {

                biblesEntities = values.get(position);

                String sdate = new Date().toString();

                String[] StringItems = {
                        sdate,
                        lang,
                        abbreviation,
                        biblesEntities.getT(),
                        book_name
                };

                int[] IntItems = {
                        version,
                        book,
                        chapter,
                        biblesEntities.getV(),
                        position
                };

                preferenceProvider.setMainFramgmentVars(StringItems, IntItems);


                final MainSheet mainSheet = new MainSheet();
                mainSheet.show(getActivity().getSupportFragmentManager(), "mainSheet");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainSheet.dismiss();
                    }
                }, 5000);
            }

            public void onLongClick(View view, int position) {
                // do nothing
            }
        }));

    }

    public static MainFragment getInstance() {
        return instance;
    }


}
