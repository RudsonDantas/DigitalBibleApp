package org.armstrong.ika.digitalbibleapp.Reference;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.armstrong.ika.digitalbibleapp.LangKeyDb.LangRepository;
import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

public class ReferenceActivity extends AppCompatActivity {

    public static ReferenceActivity instance;

    PreferenceProvider preferenceProvider;

    protected VersionRepository versionRepository;
    protected LangRepository langRepository;

    public static TextView textOne;
    private TextView textTwo;

    private String lang;

    private int version, book, chapter;

    private ReferencePageAdapter referencePageAdapter;
    private ViewPager viewPager;

    public String book_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reference_activity);

        instance = this;

        preferenceProvider = new PreferenceProvider(this);

        preferenceProvider.setVerse(1); // reset verse to 1

        int[] versionVars = preferenceProvider.getVersionVars();

        // init database
        versionRepository = new VersionRepository(this);

        version = versionVars[0];
        book = versionVars[1];
        chapter = versionVars[2];

        lang = versionRepository.getLangFromNumber(version); // lang

        preferenceProvider.setLanguageCode(lang);

        langRepository = new LangRepository(new Application());

        book_name = langRepository.getBookName(book, lang);

        ContinueSetup();

    }

    private void ContinueSetup() {

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.reference_toolbar);
        setSupportActionBar(toolbar);

        // custom title bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_activity);

        // action bar
        ActionBar ab = getSupportActionBar();
        // back arrow
        ab.setDisplayHomeAsUpEnabled(true);
        // change toolbar background colour
        ab.setBackgroundDrawable(new ColorDrawable(preferenceProvider.getColorVar()));

        String t_string = book_name + " " + chapter + ":1";

        // Action Bar Text One
        textOne = toolbar.findViewById(R.id.action_bar_title_one);
        textOne.setText(t_string);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        // Action Bar Text Two
        textTwo = toolbar.findViewById(R.id.action_bar_title_two);
        textTwo.setText("");
        textTwo.setTextColor(Color.parseColor("#FFFFFF"));
        textTwo.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        referencePageAdapter = new ReferencePageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.ref_view_pager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(referencePageAdapter);

        // Tabs
        TabLayout tabLayout = findViewById(R.id.ref_tabs);
        tabLayout.getTabAt(0).setText(R.string.tab_book);
        tabLayout.getTabAt(1).setText(R.string.tab_chapter);
        tabLayout.getTabAt(2).setText(R.string.tab_verse);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public static class ReferencePageAdapter extends FragmentPagerAdapter {

        private int pages = 3;

        public ReferencePageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return ReferenceFragOne.newInstance();
                case 1:
                    return ReferenceFragTwo.newInstance();
                case 2:
                    return ReferenceFragThree.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // number of pages.
            return pages;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.onBackPressed();
                break;

            default:
                break;
        }
        return true;
    }

    public static ReferenceActivity getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReferenceActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
