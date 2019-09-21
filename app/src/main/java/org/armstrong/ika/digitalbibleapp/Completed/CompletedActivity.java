package org.armstrong.ika.digitalbibleapp.Completed;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.armstrong.ika.digitalbibleapp.More.MoreActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

public class CompletedActivity extends AppCompatActivity {

    public static CompletedActivity instance;

    PreferenceProvider preferenceProvider;

    private static TextView textOne;
    private static TextView textTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_activity);

        instance = this;

        preferenceProvider = new PreferenceProvider(this);

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.completed_toolbar);
        setSupportActionBar(toolbar);

        // custom title bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_activity);

        // Action Bar Text One
        textOne = new TextView(this);
        textOne.setText(R.string.completed);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(preferenceProvider.getActionBarTextSizeVar());

        textTwo = new TextView(this);
        textTwo.setText("");

        ActionBar ab = getSupportActionBar();
        // back arrow
        ab.setDisplayHomeAsUpEnabled(true);
        // change toolbar text
        ab.setCustomView(textOne);
        // change toolbar color
        ab.setBackgroundDrawable(new ColorDrawable(preferenceProvider.getColorVar()));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.highlight_frame_layout, CompletedFragment.newInstance())
                .commitNow();


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public static CompletedActivity getInstance() {
        return instance;
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
        Intent moreActivity = new Intent(CompletedActivity.this, MoreActivity.class);
        startActivity(moreActivity);
    }
}
