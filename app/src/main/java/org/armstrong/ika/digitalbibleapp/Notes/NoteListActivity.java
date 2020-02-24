package org.armstrong.ika.digitalbibleapp.Notes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

public class NoteListActivity extends AppCompatActivity {

    protected PreferenceProvider preferenceProvider;

    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        preferenceProvider = new PreferenceProvider(this);

        Bundle extras = getIntent().getExtras();
        action = extras.getString("action");

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
        textOne.setText(R.string.add_note);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(actionBarTextSize);

        // Action Bar Text Two
        TextView textTwo = toolbar.findViewById(R.id.action_bar_title_two);
        textTwo.setText("");
        textTwo.setTextColor(Color.parseColor("#FFFFFF"));
        textTwo.setTextSize(actionBarTextSize);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.noteFragment, NoteListFragment.newInstance(action))
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

        Intent returnIntent = new Intent(NoteListActivity.this, NotesActivity.class);
        startActivity(returnIntent);
    }
}
