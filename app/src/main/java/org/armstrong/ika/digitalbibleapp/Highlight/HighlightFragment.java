package org.armstrong.ika.digitalbibleapp.Highlight;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;

import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightRepository;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightViewModel;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HighlightFragment extends Fragment {

    public static HighlightFragment instance;

    protected PreferenceProvider preferenceProvider;
    protected HighlightRepository highlightRepository;
    protected HighlightViewModel highlightViewModel;

    private RecyclerView recyclerView;
    private HighlightFragmentAdapter highlightFragmentAdapter;

    private List<HighlightEntities> values;

    public static HighlightFragment newInstance() {

        HighlightFragment highlightFragment = new HighlightFragment();

        return highlightFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        preferenceProvider = new PreferenceProvider(getContext());

        highlightRepository = new HighlightRepository(getContext());

        highlightViewModel = ViewModelProviders.of(getActivity())
                .get(HighlightViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.highlight_fragment, parent, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.HighlightRecyclerView);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        highlightFragmentAdapter = new HighlightFragmentAdapter(preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(highlightFragmentAdapter);

        highlightViewModel.getAllHighlights().observe(getActivity(), new Observer<List<HighlightEntities>>() {
            @Override
            public void onChanged(List<HighlightEntities> highlightEntities) {
                highlightFragmentAdapter.setValues(highlightEntities);
                values = highlightEntities;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                HighlightEntities highlights = values.get(position);

                StringBuilder sb = new StringBuilder();

                sb.append(highlights.getAbbreviation());
                sb.append(" ");
                sb.append(highlights.getBookname());
                sb.append(" ");
                sb.append(highlights.getChapter());
                sb.append(":");
                sb.append(highlights.getVerse());

                String [] StringItems ={sb.toString()};

                int [] IntItems = {
                        highlights.getId(),
                        highlights.getVersion(),
                        highlights.getBook(),
                        highlights.getChapter(),
                        highlights.getVerse(),
                        position
                };

                preferenceProvider.setHighlightVars(StringItems, IntItems);

                final HighlightSheet highlightSheet = new HighlightSheet();
                highlightSheet.show(getActivity().getSupportFragmentManager(), "highlightSheet");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        highlightSheet.dismiss();
                    }
                }, 5000);

            }

            // long click
            public void onLongClick(View view, int position) {
                // do nothing
            }

        }));

    }

    public static HighlightFragment getInstance() {
        return instance;
    }

    public void deleteHighlight(int id) {

        highlightRepository.deleteHighlight(id);
    }

    public void updateHighlightsRecyclerView(int target) {

        highlightViewModel.getAllHighlights().observe(getActivity(), new Observer<List<HighlightEntities>>() {
            @Override
            public void onChanged(List<HighlightEntities> highlightEntities) {
                highlightFragmentAdapter.setValues(highlightEntities);
                values = highlightEntities;
            }
        });

        recyclerView.smoothScrollToPosition(target);

    }

}
