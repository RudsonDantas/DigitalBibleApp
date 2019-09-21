package org.armstrong.ika.digitalbibleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import org.armstrong.ika.digitalbibleapp.Active.ActiveSheet;
import org.armstrong.ika.digitalbibleapp.BiblesDb.BiblesRepository;
import org.armstrong.ika.digitalbibleapp.Bookmark.BookmarkActivity;
import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.LangKeyDb.LangRepository;
import org.armstrong.ika.digitalbibleapp.Main.MainFragment;
import org.armstrong.ika.digitalbibleapp.Main.MainNotice;
import org.armstrong.ika.digitalbibleapp.More.MoreActivity;
import org.armstrong.ika.digitalbibleapp.Notes.NotesUpgrade;
import org.armstrong.ika.digitalbibleapp.Reference.ReferenceActivity;
import org.armstrong.ika.digitalbibleapp.Searches.SearchesActivity;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    protected PreferenceProvider preferenceProvider;

    protected VersionRepository versionRepository;

    private ViewPager vpPager;
    private FragmentPagerAdapter fragmentPageAdapter;

    private int chapter_count;

    public String lang;
    public String abbreviation;
    public String book_name;

    private TextView textOne;
    public TextView textTwo;

    private int chap_pos;

    MainFragment mainFragment;

    private AHBottomNavigation bottomNavigation;

    private int[] versionVars;

    protected BiblesRepository biblesRepository;

    protected LangRepository langRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        instance = this;

        //CompletedRepository completedRepository = new CompletedRepository(this);
        //completedRepository.deleteCompletedData();

        preferenceProvider = new PreferenceProvider(this);
        versionVars = preferenceProvider.getVersionVars();

        // init databases
        versionRepository = new VersionRepository(this);

        biblesRepository = new BiblesRepository(this);
        biblesRepository.initialize(versionVars[0]);

        langRepository = new LangRepository(this);

        // update databases
        new UpdateProvider(this).initialize(versionRepository);

        // upgrade notes
        new NotesUpgrade(this).doNotesUpgrade();

        lang = versionRepository.getLangFromNumber(versionVars[0]); // lang

        abbreviation = versionRepository.getAbbreviation(versionVars[0]); // KJV

        chapter_count = biblesRepository.getChapterCount(versionVars[1]);

        book_name = langRepository.getBookName(versionVars[1], lang);

        ContinueSetup();

    }

    private void ContinueSetup() {

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // custom title bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_activity);

        // action bar
        ActionBar ab = getSupportActionBar();
        // change toolbar background colour
        ab.setBackgroundDrawable(new ColorDrawable(preferenceProvider.getColorVar()));

        String t_string = book_name + " " + versionVars[2];

        // used in onPause
        chap_pos = versionVars[2];

        // Action Bar Text One
        textOne = toolbar.findViewById(R.id.action_bar_title_one);
        textOne.setText(t_string);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        // make Text One clickable
        textOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ReferenceActivity.class);
                startActivity(intent);
            }
        });

        // Action Bar Text Two
        textTwo = toolbar.findViewById(R.id.action_bar_title_two);
        textTwo.setText(abbreviation);
        textTwo.setTextColor(Color.parseColor("#FFFFFF"));
        textTwo.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        // make Text Two clickable
        textTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final int av_count = versionRepository.countActiveVersions();

                if (av_count > 1) {

                    final ActiveSheet activeSheet = new ActiveSheet();
                    activeSheet.show(getSupportFragmentManager(), "activeSheet");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activeSheet.dismiss();
                        }
                    }, 5000);

                } else {
                    activeVersionsWarning();
                }

            }

        });

        // Bottom navigation
        bottomNavigation = findViewById(R.id.bottom_navigation);

        int leftTitle = R.string.blank;
        int leftIcon = R.drawable.ic_bookmark_black_24dp;
        int leftColor = R.color.colorBottomNavigationActiveColored;

        // Bottom Menu
        AHBottomNavigationItem bookmark_item;
        bookmark_item = new AHBottomNavigationItem(leftTitle, leftIcon, leftColor);

        int midTitle = R.string.blank;
        int midIcon = R.drawable.ic_local_library_black_24dp;
        int midColor = R.color.colorBottomNavigationDisable;

        AHBottomNavigationItem bible_item;
        bible_item = new AHBottomNavigationItem(midTitle, midIcon, midColor);

        int rightTitle = R.string.blank;
        int rightIcon = R.drawable.ic_more_black_24dp;
        int rightColor = R.color.colorBottomNavigationNotification;

        AHBottomNavigationItem settings_item;
        settings_item = new AHBottomNavigationItem(rightTitle, rightIcon, rightColor);

        // Add items
        bottomNavigation.addItem(bookmark_item);
        bottomNavigation.addItem(bible_item);
        bottomNavigation.addItem(settings_item);

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

        // Change colors
        bottomNavigation.setAccentColor(preferenceProvider.getColorVar());
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Manage titles
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(1);

        // Quick return animation
        bottomNavigation.setBehaviorTranslationEnabled(true);

        bookMarkNotification();

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        if (!wasSelected) {
                            Intent intent = new Intent(getApplicationContext(), BookmarkActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        if (!wasSelected) {
                            Utils.makeToast(getApplicationContext(), "text");
                        }
                        break;
                    case 2:
                        Intent intent = new Intent(getApplicationContext(), MoreActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        // initialise ViewPager
        vpPager = findViewById(R.id.vpPager);

        fragmentPageAdapter = new LocalPagerAdapter(getSupportFragmentManager());

        vpPager.setAdapter(fragmentPageAdapter);

        vpPager.setCurrentItem(versionVars[2] - 1, true);

        //currentItem = vpPager.getCurrentItem();

        // Attach the page change listener inside the activity
        vpPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

                chap_pos = position + 1;

                // update title bar
                String title_string = book_name + " " + chap_pos;
                textOne.setText(title_string);

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                versionVars[3] = 1;
                MainFragment.getInstance().target = 0;
                //MainFragment.getInstance().scrollToTarget(0);

            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected void onPause() {
        super.onPause();

        preferenceProvider.setChapter(chap_pos);

    }

    public void refreshViewPager(int pos) {

        preferenceProvider.setVerse(pos + 1);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public class LocalPagerAdapter extends FragmentPagerAdapter {

        public LocalPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return chapter_count;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            mainFragment = MainFragment.newInstance(
                    position,
                    versionVars[3], // verse
                    versionVars[1], //book
                    abbreviation,
                    lang,
                    book_name,
                    versionVars[0] // version
            );

            return mainFragment;
        }


    }

    public void bookMarkNotification() {

        final String c;
        c = Integer.toString(preferenceProvider.getBookmarkCount());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText(c)
                        .setBackgroundColor(Color.YELLOW)
                        .setTextColor(Color.BLACK)
                        .build();

                // Adding notification to last item.
                bottomNavigation.setNotification(notification, bottomNavigation.getItemsCount() - 3);

                //notificationVisible = true;
            }
        }, 500);
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        onBackPressedWarning(MainActivity.getInstance());
    }

    private void onBackPressedWarning(final AppCompatActivity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(R.string.back_pressed_title);
        builder.setMessage(R.string.back_pressed_message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });
        builder.create();
        builder.show();

    }

    public void unlockVersions() {
        versionRepository.unlockVersions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent searchesIntent = new Intent(MainActivity.this, SearchesActivity.class);
            startActivity(searchesIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void activeVersionsWarning() {

        final MainNotice mainNotice = new MainNotice();
        mainNotice.show(this.getSupportFragmentManager(), "mainNotice");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainNotice.dismiss();
            }
        }, 5000);
    }


}
