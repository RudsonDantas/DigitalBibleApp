package org.armstrong.ika.digitalbibleapp.More;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import org.armstrong.ika.digitalbibleapp.Bookmark.BookmarkActivity;

import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

public class MoreActivity extends AppCompatActivity {

    public static MoreActivity instance;

    PreferenceProvider preferenceProvider;

    private ActionBar ab;

    public TextView textOne;
    public TextView textTwo;

    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_activity);

        instance = this;

        preferenceProvider = new PreferenceProvider(this);
        //preferenceProvider.setVerse(1); // reset verse position

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.more_toolbar);
        setSupportActionBar(toolbar);

        // custom title bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_activity);

        // action bar
        ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(preferenceProvider.getColorVar()));

        // Action Bar Text One
        textOne = toolbar.findViewById(R.id.action_bar_title_one);
        textOne.setText(R.string.more);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        textTwo = toolbar.findViewById(R.id.action_bar_title_two);
        textTwo.setText("");

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
        bottomNavigation.setCurrentItem(2);

        // Quick return animation
        bottomNavigation.setBehaviorTranslationEnabled(false);

        bookMarkNotification();

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        if (!wasSelected) {
                            Intent intent = new Intent(MoreActivity.this, BookmarkActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        if (!wasSelected) {
                            Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        if (!wasSelected) {

                        }
                        break;
                }


                return true;
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.more_frame_layout, MoreFragment.newInstance())
                .commitNow();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void bookMarkNotification() {

        int cnt = preferenceProvider.getBookmarkCount();

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

    public static MoreActivity getInstance() {
        return instance;
    }

    public void setBackgroundColors(int selectedColor) {
        // change toolbar colour
        ab.setBackgroundDrawable(new ColorDrawable(selectedColor));
        // bottom navigation active icon
        bottomNavigation.setAccentColor(selectedColor);
        // update settings colour
        preferenceProvider.setColor(selectedColor);
    }

    public void updateLangTextOne(String lang) {

        textOne.setText(R.string.more);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MoreActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
