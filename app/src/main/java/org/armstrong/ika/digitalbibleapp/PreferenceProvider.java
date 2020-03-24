package org.armstrong.ika.digitalbibleapp;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class PreferenceProvider {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    // vars version
    private int VARS_VERSION;

    // vars save name
    private String VARS_SAVE_VERSION_NAME = "varSaveVersionName";


    public PreferenceProvider(Context context) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // delete vars if changed
        initializeUpdate(7);

    }

    public int getColorVar() {
        return sharedPreferences.getInt("color", R.color.colorPrimaryDark);
    }

    public int gettextSizeVar() {
        return sharedPreferences.getInt("textSize", 16);
    }

    public int getActionBarTextSizeVar() {
        return sharedPreferences.getInt("textSize", 16) - 2;
    }

    public String getlanguageCodeVar() {
        return sharedPreferences.getString("languageCode", "eng");
    }

    public void setMainFramgmentVars(String[] StringItems, int[] IntItems) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("date", StringItems[0]);
        sharedPreferencesEditor.putString("lang", StringItems[1]);
        sharedPreferencesEditor.putString("abbreviation", StringItems[2]);
        sharedPreferencesEditor.putString("text", StringItems[3]);
        sharedPreferencesEditor.putString("book_name", StringItems[4]);

        sharedPreferencesEditor.putInt("version", IntItems[0]);
        sharedPreferencesEditor.putInt("book", IntItems[1]);
        sharedPreferencesEditor.putInt("chapter", IntItems[2]);
        sharedPreferencesEditor.putInt("verse", IntItems[3]);
        sharedPreferencesEditor.putInt("position", IntItems[4]);
        sharedPreferencesEditor.apply();

    }

    public int[] getPositionVars() {

        int[] positionVars = {
                sharedPreferences.getInt("position", 0)
        };

        return positionVars;
    }

    public void setLanguageCode(String lang) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("languageCode", lang);
        sharedPreferencesEditor.apply();
    }

    public void setTextSize(int size) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("textSize", size);
        sharedPreferencesEditor.apply();
    }

    public void setChapter(int pos) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("chapter", pos);
        sharedPreferencesEditor.apply();
    }

    public int getChapter() {

        return sharedPreferences.getInt("chapter", 1);
    }

    public String getLang() {

        return sharedPreferences.getString("lang", "en");
    }

    public int getSearchArea() {

        return sharedPreferences.getInt("searchArea", 0);
    }

    public void setSearchArea(int pos) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("searchArea", pos);
        sharedPreferencesEditor.apply();
    }


    public int getBook() {

        return sharedPreferences.getInt("book", 1);
    }

    public void setVerse(int pos) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("verse", pos);
        sharedPreferencesEditor.apply();
    }

    public void setSearchVars(String[] StringItems, int[] IntItems) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("search_book", IntItems[0]);
        sharedPreferencesEditor.putInt("search_chapter", IntItems[1]);
        sharedPreferencesEditor.putInt("search_verse", IntItems[2]);
        sharedPreferencesEditor.putString("search_text", StringItems[0]);
        sharedPreferencesEditor.putString("search_book_name", StringItems[1]);
        sharedPreferencesEditor.putString("search_abbreviation", StringItems[2]);
        sharedPreferencesEditor.apply();
    }

    public String[] getStringSearchVars() {

        String[] searchVars = {

                sharedPreferences.getString("search_abbreviation", ""),
                sharedPreferences.getString("search_text", ""),
                sharedPreferences.getString("search_book_name", "")
        };

        return searchVars;

    }

    public int[] getSearchVars() {

        int[] searchVars = {

                sharedPreferences.getInt("search_book", 43),
                sharedPreferences.getInt("search_chapter", 1),
                sharedPreferences.getInt("search_verse", 1)
        };

        return searchVars;

    }

    public void setBookVars(int[] IntItems) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("book", IntItems[0]);
        sharedPreferencesEditor.putInt("chapter", IntItems[1]);
        sharedPreferencesEditor.putInt("verse", IntItems[2]);
        sharedPreferencesEditor.apply();
    }

    public int[] getBookVars() {

        int[] bookVars = {

                sharedPreferences.getInt("book", 43),
                sharedPreferences.getInt("chapter", 1),
                sharedPreferences.getInt("verse", 1)
        };

        return bookVars;

    }

    public void setVersionVars(int[] IntItems) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("version", IntItems[0]);
        sharedPreferencesEditor.putInt("book", IntItems[1]);
        sharedPreferencesEditor.putInt("chapter", IntItems[2]);
        sharedPreferencesEditor.putInt("verse", IntItems[3]);
        sharedPreferencesEditor.apply();
    }

    public int[] getVersionVars() {

        int[] versionVars = {

                sharedPreferences.getInt("version", 1),
                sharedPreferences.getInt("book", 43),
                sharedPreferences.getInt("chapter", 1),
                sharedPreferences.getInt("verse", 1)
        };

        return versionVars;

    }

    public void setBookmarkVars(String[] StringItems, int[] IntItems) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("bookMarkID", IntItems[0]);
        sharedPreferencesEditor.putString("bookMarkRef", StringItems[0]);
        sharedPreferencesEditor.apply();
    }

    public int getBookmarkId() {

        return sharedPreferences.getInt("bookMarkID", 0);
    }

    public String[] getBookmarkRef() {

        String[] bookmarkRef = {
                sharedPreferences.getString("bookMarkRef", ""),
        };

        return bookmarkRef;
    }

    public String[] getNoteVars() {

        String[] noteVars = {
                sharedPreferences.getString("noteID", ""),
                sharedPreferences.getString("noteRef", ""),
                sharedPreferences.getString("noteText", "")
        };

        return noteVars;
    }

    public Integer[] getNoteIntVars() {

        Integer[] noteIntVars = {
                sharedPreferences.getInt("noteVersion", 1),
                sharedPreferences.getInt("noteBook", 42),
                sharedPreferences.getInt("noteChapter", 1),
                sharedPreferences.getInt("noteVerse", 1)
        };

        return noteIntVars;
    }

    public String[] getCompareVars() {

        String[] compareVars = {
                sharedPreferences.getString("abbreviation", ""),
                sharedPreferences.getString("book_name", ""),
                sharedPreferences.getString("text", "")
        };

        return compareVars;
    }

    public int getVersion() {

        return sharedPreferences.getInt("version", 1);
    }

    public String[] getBookmarkVars() {

        String[] bookMarkVars = {
                sharedPreferences.getString("date", ""),
                sharedPreferences.getString("lang", ""),
                sharedPreferences.getString("abbreviation", ""),
                sharedPreferences.getString("text", ""),
                sharedPreferences.getString("book_name", "")
        };

        return bookMarkVars;

    }

    public void setHighlightVars(String[] StringItems, int[] IntItems) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("highlight_id", IntItems[0]);
        sharedPreferencesEditor.putInt("highlight_version", IntItems[1]);
        sharedPreferencesEditor.putInt("highlight_book", IntItems[2]);
        sharedPreferencesEditor.putInt("highlight_chapter", IntItems[3]);
        sharedPreferencesEditor.putInt("highlight_verse", IntItems[4]);
        sharedPreferencesEditor.putInt("highlight_pos", IntItems[5]);
        sharedPreferencesEditor.putString("highlight_ref", StringItems[0]);
        sharedPreferencesEditor.apply();
    }

    public int[] getHighlightVars() {

        int[] highlightVars = {

                sharedPreferences.getInt("highlight_id", 0),
                sharedPreferences.getInt("highlight_version", 1),
                sharedPreferences.getInt("highlight_book", 1),
                sharedPreferences.getInt("highlight_chapter", 1),
                sharedPreferences.getInt("highlight_verse", 1),
                sharedPreferences.getInt("highlight_pos", 0)

        };

        return highlightVars;

    }

    public String[] getHighlightRef() {

        String[] highlightRef = {
                sharedPreferences.getString("highlight_ref", "")
        };

        return highlightRef;
    }

    public void setWheelStyle(String ws) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("wheelStyle", ws);
        sharedPreferencesEditor.apply();
    }

    public String getWheelStyle() {
        return sharedPreferences.getString("wheelStyle", "0");
    }

    public void setVersion(int version) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("version", version);
        sharedPreferencesEditor.apply();
    }

    public void setNoteVars(String[] StringVars) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("noteID", StringVars[0]);
        sharedPreferencesEditor.putString("noteRef", StringVars[1]);
        sharedPreferencesEditor.putString("noteText", StringVars[2]);
        sharedPreferencesEditor.apply();
    }

    public void setNoteIntVars(int[] IntItems) {

        sharedPreferencesEditor.putInt("noteVersion", IntItems[0]);
        sharedPreferencesEditor.putInt("noteBook", IntItems[1]);
        sharedPreferencesEditor.putInt("noteChapter", IntItems[2]);
        sharedPreferencesEditor.putInt("noteVerse", IntItems[3]);
        sharedPreferencesEditor.apply();
    }

    public void setColor(int color) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("color", color);
        sharedPreferencesEditor.apply();
    }

    public void setBookmarkCount(int cnt) {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt("bookMarkCount", cnt);
        sharedPreferencesEditor.apply();
    }

    public int getBookmarkCount() {

        return sharedPreferences.getInt("bookMarkCount", 0);
    }

    // update vars
    public void initializeUpdate(int ver) {

        VARS_VERSION = ver;

        if (installedVarsAreOutdated()) {

            sharedPreferencesEditor = sharedPreferences.edit();

            sharedPreferencesEditor.remove("version");
            sharedPreferencesEditor.remove("book");
            sharedPreferencesEditor.remove("chapter");
            sharedPreferencesEditor.remove("verse");
            sharedPreferencesEditor.remove("textSize");
            sharedPreferencesEditor.remove("color");
            sharedPreferencesEditor.remove("bookMarkID");
            sharedPreferencesEditor.remove("noteID");

            sharedPreferencesEditor.putInt(VARS_SAVE_VERSION_NAME, VARS_VERSION);
            sharedPreferencesEditor.apply();

        }

    }

    private boolean installedVarsAreOutdated() {

        int version = sharedPreferences.getInt(VARS_SAVE_VERSION_NAME, 0);

        return (version < VARS_VERSION) ? true : false;

    }

}