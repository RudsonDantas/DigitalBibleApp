package org.armstrong.ika.digitalbibleapp.Searches;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesEntities;
import org.armstrong.ika.digitalbibleapp.HightlightDb.HighlightEntities;
import org.armstrong.ika.digitalbibleapp.LangKeyDb.LangRepository;
import org.armstrong.ika.digitalbibleapp.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchesFragmentAdapter extends RecyclerView.Adapter<SearchesFragmentAdapter.CustomViewHolder> {

    protected LangRepository langRepository;
    private List<BiblesEntities> values = new ArrayList<>();
    private List<HighlightEntities> highlights;

    private int TextSize;
    private String searchString;
    private String lang;
    Context context;

    public SearchesFragmentAdapter(List<HighlightEntities> highlights, int textsize,
                                   String searchString, Context context, String lang) {
        this.TextSize = textsize;
        this.searchString = searchString.toLowerCase(Locale.getDefault());
        this.lang = lang;
        this.context = context;
        this.highlights = highlights;

        this.langRepository = new LangRepository(new Application());

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.searches_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder((view));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final BiblesEntities biblesEntities = values.get(position);

        String book_name = langRepository.getBookName(biblesEntities.getB(), lang);

        String reference = book_name + " " + biblesEntities.getC() + ":" + biblesEntities.getV();

        customViewHolder.searchReference.setText(reference);
        customViewHolder.searchReference.setTextSize(TextSize);

        if (highlights.size() > 0) {

            for (int i = 0; i < highlights.size(); i++) {

                HighlightEntities highlightEntities = highlights.get(i);

                if (biblesEntities.getB() == highlightEntities.getBook()
                        && biblesEntities.getC() == highlightEntities.getChapter()
                        && biblesEntities.getV() == highlightEntities.getVerse()) {

                    Spannable spannable = new SpannableString(biblesEntities.getT());
                    spannable.setSpan(new BackgroundColorSpan(highlightEntities.getColor()), 0, biblesEntities.getT().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    customViewHolder.searchText.setText(spannable);
                    customViewHolder.searchText.setTextSize(TextSize);
                    //customViewHolder.searchText.setTextColor(Color.DKGRAY);

                    break;

                } else {

                    customViewHolder.searchText.setText(highlightWord(searchString, biblesEntities.getT()));
                    customViewHolder.searchText.setTextSize(TextSize);
                    //customViewHolder.searchText.setTextColor(Color.DKGRAY);

                }

            }

        } else {

            customViewHolder.searchText.setText(highlightWord(searchString, biblesEntities.getT()));
            customViewHolder.searchText.setTextSize(TextSize);
            //customViewHolder.searchText.setTextColor(Color.DKGRAY);

        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView searchReference;
        protected TextView searchText;

        public CustomViewHolder(final View view) {
            super(view);

            this.searchReference = view.findViewById(R.id.searchReference);
            this.searchText = view.findViewById(R.id.searchText);

        }

    }

    public void setValues(List<BiblesEntities> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static CharSequence highlightWord(String search, String originalText) {

        if (search != null && !search.equalsIgnoreCase("")) {
            String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
            int start = normalizedText.indexOf(search);
            if (start < 0) {
                return originalText;
            } else {
                Spannable highlighted = new SpannableString(originalText);
                while (start >= 0) {
                    int spanStart = Math.min(start, originalText.length());
                    int spanEnd = Math.min(start + search.length(), originalText.length());
                    highlighted.setSpan(new ForegroundColorSpan(Color.RED), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start = normalizedText.indexOf(search, spanEnd);
                }
                return highlighted;
            }
        }
        return originalText;
    }


}
