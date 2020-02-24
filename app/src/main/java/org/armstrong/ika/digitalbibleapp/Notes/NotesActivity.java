package org.armstrong.ika.digitalbibleapp.Notes;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.armstrong.ika.digitalbibleapp.More.MoreActivity;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NotesActivity extends AppCompatActivity {

    public static NotesActivity instance;

    PreferenceProvider preferenceProvider;

    private static TextView textOne;
    private static TextView textTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);

        instance = this;

        preferenceProvider = new PreferenceProvider(this);

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        // custom title bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_activity);

        // Action Bar Text One
        textOne = new TextView(this);
        textOne.setText(R.string.notes);
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(preferenceProvider.getColorVar()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // click on the FAB

                Intent noteIntent = new Intent(getApplication(), NoteListActivity.class);
                noteIntent.putExtra("action", "new");
                startActivity(noteIntent);
            }
        });


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.notes_frame_layout, NotesFragment.newInstance())
                .commitNow();


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public static NotesActivity getInstance() {
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
        Intent moreActivity = new Intent(NotesActivity.this, MoreActivity.class);
        startActivity(moreActivity);
    }
}
