package org.armstrong.ika.digitalbibleapp.Searches;

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.Main.MainNotice;
import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.Searches.Area.SearchesAreaSheet;
import org.armstrong.ika.digitalbibleapp.Searches.History.SearchesHistoryFragment;

import org.armstrong.ika.digitalbibleapp.SearchesDb.SearchesEntities;
import org.armstrong.ika.digitalbibleapp.SearchesDb.SearchesRepository;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

import java.util.Arrays;
import java.util.Date;

public class SearchesActivity extends AppCompatActivity {

    private static SearchesActivity instance;

    private PreferenceProvider preferenceProvider;

    protected SearchesRepository searchesRepository;
    protected VersionRepository versionRepository;

    private TextView textOne, textTwo;

    public SearchView searchView;
    public ImageView closeButton;
    public EditText searchText;

    private String abbreviation;
    public int version;
    private String lang;

    private int textSize;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searches_activity);

        instance = this;

        preferenceProvider = new PreferenceProvider(this);

        color = preferenceProvider.getColorVar();
        version = preferenceProvider.getVersion();
        textSize = preferenceProvider.gettextSizeVar();

        searchesRepository = new SearchesRepository(this);
        searchesRepository.deleteSearchesByDate(Utils.calculateOffset());

        versionRepository = new VersionRepository(this);

        lang = versionRepository.getLangFromNumber(version); // lang

        abbreviation = versionRepository.getAbbreviation(version); // KJV

        // search view
        searchView = findViewById(R.id.searchesInput);

        // SearchesHistoryFragment click sets iconified = false
        // SearchesFragment onclick clears focus

        // input text field
        searchText = searchView.findViewById(R.id.search_src_text);

        // listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                query = Utils.escapeString(query).trim();

                String[] stopWords = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                        "s", "t", "u", "v", "w", "x", "y", "z", "the", "and", "is", "was", "be", "been", "then", "there"};

                boolean contains = Arrays.asList(stopWords).contains(query.toLowerCase());

                if (contains) {

                    String search_notice = R.string.notice + "\n" + R.string.search_notice;

                    Utils.makeToast(getApplicationContext(), search_notice);


                } else {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.searches_frame_layout, SearchesFragment.newInstance(query, abbreviation, lang))
                            .commitNow();

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        // Catch event on [x] button inside search view
        closeButton = searchView.findViewById(R.id.search_close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // clear search field
                searchText.setText("");

                hideSoftKeyboard(searchText);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.searches_frame_layout, SearchesHistoryFragment.newInstance())
                        .commitNow();

            }
        });

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.searches_toolbar);
        setSupportActionBar(toolbar);

        // custom title bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_activity);

        // action bar
        ActionBar ab = getSupportActionBar();
        // back arrow
        ab.setDisplayHomeAsUpEnabled(true);
        // change toolbar background colour
        ab.setBackgroundDrawable(new ColorDrawable(color));

        // Action Bar Text One
        textOne = toolbar.findViewById(R.id.action_bar_title_one);
        textOne.setText(R.string.search);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        // Action Bar Text Two
        textTwo = toolbar.findViewById(R.id.action_bar_title_two);
        textTwo.setText(abbreviation);
        textTwo.setTextColor(Color.parseColor("#FFFFFF"));
        textTwo.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        // make Text Two clickable
        textTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int av_count = versionRepository.countActiveVersions();

                if (av_count > 1) {

                    final SearchesActiveSheet searchesActiveSheet = new SearchesActiveSheet();
                    searchesActiveSheet.show(getSupportFragmentManager(), "searchesActiveSheet");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            searchesActiveSheet.dismiss();
                        }
                    }, 5000);

                } else {
                    activeVersionsWarning();
                }

            }

        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.searches_frame_layout, SearchesHistoryFragment.newInstance())
                .commitNow();

    }

    public static SearchesActivity getInstance() {
        return instance;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.onBackPressed();
                break;

            case R.id.action_area:
                final SearchesAreaSheet searchSheet = new SearchesAreaSheet();
                searchSheet.show(this.getSupportFragmentManager(), "searchSheet");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchSheet.dismiss();
                    }
                }, 5000);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

//    public void showSoftKeyboard(View view) {
//        if (view.requestFocus()) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
//        }
//    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void activeVersionsWarning() {

        searchView.clearFocus();

        final MainNotice mainNotice = new MainNotice();
        mainNotice.show(this.getSupportFragmentManager(), "mainNotice");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainNotice.dismiss();
            }
        }, 5000);
    }

    public void saveQuery(String query) {

        if (!searchesRepository.entryExits(query, version)) {

            int date = (int) (new Date().getTime() / 1000);
            SearchesEntities searchesEntities = new SearchesEntities();
            searchesEntities.setDate(date);
            searchesEntities.setVersion(version);
            searchesEntities.setText(query);

            searchesRepository.saveSearchHistory(searchesEntities);
        }
    }


}
