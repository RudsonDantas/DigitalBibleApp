package org.armstrong.ika.digitalbibleapp.About;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.armstrong.ika.digitalbibleapp.More.MoreActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    PreferenceProvider preferenceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        preferenceProvider = new PreferenceProvider(this);

        int color = preferenceProvider.getColorVar();
        int actionBarTextSize = preferenceProvider.getActionBarTextSizeVar();

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.main_toolbar);
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
        TextView textOne = toolbar.findViewById(R.id.action_bar_title_one);
        textOne.setText(R.string.about);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(actionBarTextSize);

        // Action Bar Text Two
        TextView textTwo = toolbar.findViewById(R.id.action_bar_title_two);
        textTwo.setText("");
        textTwo.setTextColor(Color.parseColor("#FFFFFF"));
        textTwo.setTextSize(actionBarTextSize);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.aboutFragment, AboutFragment.newInstance())
                .commitNow();


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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AboutActivity.this, MoreActivity.class);
        startActivity(intent);
    }
}
