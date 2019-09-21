package org.armstrong.ika.digitalbibleapp.Searches;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesEntities;
import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesRepository;

import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;

import org.armstrong.ika.digitalbibleapp.Common.Utils;

import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightRepository;
import org.armstrong.ika.digitalbibleapp.LangKeyDb.LangRepository;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchesFragment extends Fragment {

    Context context;

    private static SearchesFragment instance;

    private PreferenceProvider preferenceProvider;

    private RecyclerView recyclerView;
    private SearchesFragmentAdapter searchesFragmentAdapter;

    private LinearLayoutManager linearLayoutManager;

    protected LangRepository langRepository;

    protected BiblesRepository biblesRepository;

    protected HighlightRepository highlightRepository;

    List<HighlightEntities> highlights = new ArrayList<>();

    private View view;

    private int textSize;
    private String lang;
    private String abbreviation;

    private int start = 0;
    private int end = 0;

    private String query;

    private List<BiblesEntities> values = new ArrayList<>();

    private int[] versionVars;

    public static SearchesFragment newInstance(String query, String abbreviation, String lang) {

        SearchesFragment searchesFragment = new SearchesFragment();

        Bundle args = new Bundle();
        args.putString("query", query);
        args.putString("abbreviation", abbreviation);
        args.putString("lang", lang);
        searchesFragment.setArguments(args);

        return searchesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        lang = getArguments().getString("lang", "eng");
        abbreviation = getArguments().getString("abbreviation", "KJV");
        query = getArguments().getString("query", "");

        langRepository = new LangRepository(new Application());

        preferenceProvider = new PreferenceProvider(getContext());
        versionVars = preferenceProvider.getVersionVars();

        biblesRepository = new BiblesRepository(getContext());
        biblesRepository.initialize(versionVars[0]); //version

        textSize = preferenceProvider.gettextSizeVar();

        highlightRepository = new HighlightRepository(getContext());
        highlights = highlightRepository.getChapHighlights(versionVars[0], versionVars[1], versionVars[2]); //version, book, chapter

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.searches_fragment, container, false);

        recyclerView = view.findViewById(R.id.SearchesFragRecyclerView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int searchArea = preferenceProvider.getSearchArea();

        switch (searchArea) {

            case 0: // Law
                start = 1;
                end = 5;
                break;

            case 1: // O.T.
                start = 6;
                end = 17;
                break;

            case 2: // Wisdom
                start = 18;
                end = 22;
                break;

            case 3: // Major prophets
                start = 23;
                end = 27;
                break;

            case 4: // Minor prophets
                start = 28;
                end = 39;
                break;

            case 5: // N.T.
                start = 40;
                end = 44;
                break;

            case 6: // Paul's letters
                start = 45;
                end = 57;
                break;

            case 7: // General letters
                start = 58;
                end = 65;
                break;

            case 8: // Revelation
                start = 66;
                end = 66;
                break;
        }

        values = biblesRepository.bibleSearch(query, start, end);

        if (values.size() > 0) {  // if query was found

            searchesFragmentAdapter = new SearchesFragmentAdapter(highlights, textSize, query, context, lang);

            recyclerView.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), linearLayoutManager.VERTICAL, 16));
            recyclerView.setAdapter(searchesFragmentAdapter);

            searchesFragmentAdapter.setValues(values);

            // show number of entries found
            Utils.makeToast(getContext(), Integer.toString(values.size()));

            // save query
            SearchesActivity.getInstance().saveQuery(query);


            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                    recyclerView, new RecyclerTouchListener.ClickListener() {

                public void onClick(View view, int position) {

                    BiblesEntities biblesEntities = values.get(position);

                    int book = biblesEntities.getB();

                    String book_name = langRepository.getBookName(book, lang);

                    String StringItems[] = {biblesEntities.getT(), book_name, abbreviation};
                    int IntItems[] = {book, biblesEntities.getC(), biblesEntities.getV()};

                    preferenceProvider.setSearchVars(StringItems, IntItems);

                    final SearchesFragmentSheet searchesFragmentSheet = new SearchesFragmentSheet();
                    searchesFragmentSheet.show(getActivity().getSupportFragmentManager(), "searchesFragmentSheet");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            searchesFragmentSheet.dismiss();
                        }
                    }, 5000);


                }

                public void onLongClick(View view, int position) {
                    // do nothing
                }

            }));

        } else {
            Utils.makeToast(getContext(), getString(R.string.not_found));
        }

    }

    public void reloadSearch() {
        highlights = highlightRepository.getChapHighlights(versionVars[0], versionVars[1], versionVars[2]); //version, book, chapter
        searchesFragmentAdapter = new SearchesFragmentAdapter(highlights, textSize, query, context, lang);
        recyclerView.setAdapter(searchesFragmentAdapter);
        searchesFragmentAdapter.setValues(values);
    }

    public static SearchesFragment getInstance() {
        return instance;
    }


}
