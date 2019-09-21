package org.armstrong.ika.digitalbibleapp.Bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkRepository;
import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkEntities;
import org.armstrong.ika.digitalbibleapp.BookmarkDb.BookmarkViewModel;
import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

public class BookmarkFragment extends Fragment {

    protected PreferenceProvider preferenceProvider;

    protected BookmarkRepository bookmarkRepository;

    protected BookmarkViewModel bookmarkViewModel;

    private RecyclerView recyclerView;
    private BookmarkFragmentAdapter bookmarkFragmentAdapter;

    private List<BookmarkEntities> bookmarks;

    private View view;

    public static BookmarkFragment newInstance() {

        BookmarkFragment bookmarkFragment = new BookmarkFragment();
        return bookmarkFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

        bookmarkRepository = new BookmarkRepository(getContext());

        bookmarkViewModel = ViewModelProviders.of(getActivity())
                .get(BookmarkViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bookmark_fragment, parent, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.BookmarkRecyclerView);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bookmarkFragmentAdapter = new BookmarkFragmentAdapter(preferenceProvider.gettextSizeVar());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));

        recyclerView.setAdapter(bookmarkFragmentAdapter);

        bookmarkViewModel.getAllBookmarks().observe(getActivity(), new Observer<List<BookmarkEntities>>() {
            @Override
            public void onChanged(List<BookmarkEntities> bookmarkEntities) {
                bookmarkFragmentAdapter.setBookmarks(bookmarkEntities);
                bookmarks = bookmarkEntities;
            }
        });

        // click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                BookmarkEntities bookmarkModel = bookmarks.get(position);

                StringBuilder ref = new StringBuilder();

                ref.append(bookmarkModel.getAbbreviation());
                ref.append(" ");
                ref.append(bookmarkModel.getBook_name());
                ref.append(" ");
                ref.append(bookmarkModel.getChapter());
                ref.append(":");
                ref.append(bookmarkModel.getVerse());

                String[] StringItems = {ref.toString()};

                int[] IntItems = {
                        bookmarkModel.getVersion(),
                        bookmarkModel.getBook(),
                        bookmarkModel.getChapter(),
                        bookmarkModel.getVerse()
                };

                int[] BIntItems = {
                        bookmarkModel.getId()
                };

                preferenceProvider.setVersionVars(IntItems);

                preferenceProvider.setBookmarkVars(StringItems, BIntItems);

                BookmarkSheet bookmarkSheet = new BookmarkSheet();
                bookmarkSheet.show(getActivity().getSupportFragmentManager(), "bookmarkSheet");

            }

            // long click
            public void onLongClick(View view, int position) {
                // do nothing

            }

        }));

    }

}
