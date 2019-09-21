package org.armstrong.ika.digitalbibleapp.Reference;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.LangKeyDb.LangEntities;

import org.armstrong.ika.digitalbibleapp.LangKeyDb.LanguageKeyViewModel;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReferenceFragOne extends Fragment {

    private static ReferenceFragOne instance;

    protected PreferenceProvider preferenceProvider;

    protected LanguageKeyViewModel languageKeyViewModel;

    private RecyclerView recyclerView;
    private ReferenceFragOneAdapter referenceFragOneAdapter;

    private List<LangEntities> values;

    private String lang;
    private int textSize;

    public String book_name;

    private SearchView searchView;

    View view;

    public static ReferenceFragOne newInstance() {
        ReferenceFragOne referenceFragOne = new ReferenceFragOne();
        return referenceFragOne;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        languageKeyViewModel = ViewModelProviders.of(getActivity())
                .get(LanguageKeyViewModel.class);

        book_name = ReferenceActivity.getInstance().book_name;

        preferenceProvider = new PreferenceProvider(getContext());

        lang = preferenceProvider.getlanguageCodeVar();
        textSize = preferenceProvider.gettextSizeVar();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.reference_fragone, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.ReferenceFragRecyclerView);

        searchView = view.findViewById(R.id.book_name_search);
        searchView.setIconified(true);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        referenceFragOneAdapter = new ReferenceFragOneAdapter(textSize);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(referenceFragOneAdapter);

        languageKeyViewModel.getAllReferences(lang).observe(getActivity(), new Observer<List<LangEntities>>() {
            @Override
            public void onChanged(List<LangEntities> langEntities) {
                referenceFragOneAdapter.setValues(langEntities);
                values = langEntities;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                LangEntities langEntities = values.get(position);

                book_name = langEntities.getName();

                String title =  book_name + " 1:1";

                ReferenceActivity.getInstance().textOne.setText(title);

                int[] bookVars = {langEntities.getNumber(), 1, 1};

                preferenceProvider.setBookVars(bookVars);

                // update chapters
                ReferenceFragTwo.getInstance().updateChapterGrid();
                // update verses
                ReferenceFragThree.getInstance().updateVerseGrid();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // move to chapter tab
                        TabLayout tabhost = getActivity().findViewById(R.id.ref_tabs);
                        tabhost.getTabAt(1).select();

                    }
                }, 200);

            }

            // long click
            public void onLongClick(View view, int position) {
                // do nothing
            }

        }));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                languageKeyViewModel.getBookSearch(newText, lang).observe(getActivity(), new Observer<List<LangEntities>>() {
                    @Override
                    public void onChanged(List<LangEntities> langEntities) {
                        referenceFragOneAdapter.setValues(langEntities);
                        values = langEntities;
                    }
                });

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                        recyclerView, new RecyclerTouchListener.ClickListener() {

                    public void onClick(View view, int position) {

                        searchView.setIconified(true);
                        searchView.clearFocus();

                        LangEntities langEntities = values.get(position);

                        book_name = langEntities.getName();

                        String title =  book_name + " 1:1";

                        ReferenceActivity.getInstance().textOne.setText(title);

                        int[] bookVars = {langEntities.getNumber(), 1, 1};

                        preferenceProvider.setBookVars(bookVars);

                        // update chapters
                        ReferenceFragTwo.getInstance().updateChapterGrid();
                        // update verses
                        ReferenceFragThree.getInstance().updateVerseGrid();

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // move to chapter tab
                                TabLayout tabhost = getActivity().findViewById(R.id.ref_tabs);
                                tabhost.getTabAt(1).select();

                            }
                        }, 200);

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        // do nothing
                    }

                }));

                return false;
            }
        });


    }

    public static ReferenceFragOne getInstance() {
        return instance;
    }



}
