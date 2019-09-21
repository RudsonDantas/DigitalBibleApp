package org.armstrong.ika.digitalbibleapp.Bookmark;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.More.MoreActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BookmarkActivity extends AppCompatActivity {

    public static BookmarkActivity instance;

    PreferenceProvider preferenceProvider;

    private static TextView textOne;
    private static TextView textTwo;

    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_activity);

        instance = this;

        preferenceProvider = new PreferenceProvider(this);
        //preferenceProvider.setVerse(1); // reset verse position

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

        // Change colors
        bottomNavigation.setAccentColor(preferenceProvider.getColorVar());
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Manage titles
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);

        // Quick return animation
        bottomNavigation.setBehaviorTranslationEnabled(false);

        bookMarkNotification();

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        if (!wasSelected) {

                        }
                        break;
                    case 1:
                        if (!wasSelected) {
                            Intent intent = new Intent(BookmarkActivity.this, MainActivity.class);
                            startActivity(intent);
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

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.bookmark_toolbar);
        setSupportActionBar(toolbar);

        // custom title bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_activity);

        // Action Bar Text One
        textOne = new TextView(this);
        textOne.setText(R.string.bookmarks);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        textTwo = new TextView(this);
        textTwo.setText("");

        ActionBar ab = getSupportActionBar();
        //ab.setDisplayHomeAsUpEnabled(true);
        // change toolbar text
        ab.setCustomView(textOne);
        // change toolbar color
        ab.setBackgroundDrawable(new ColorDrawable(preferenceProvider.getColorVar()));


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bookmark_frame_layout, BookmarkFragment.newInstance())
                .commitNow();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void bookMarkNotification() {

        int cnt;
        cnt = preferenceProvider.getBookmarkCount();

        if (cnt >= 0) {

            final String c = Integer.toString(cnt);

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

    }

    public static BookmarkActivity getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookmarkActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
