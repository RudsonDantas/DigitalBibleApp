package org.armstrong.ika.digitalbibleapp.HightlightDb;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HighlightRepository {

    protected HighlightDoa highlightDoa;
    private List<HighlightEntities> chapHighLights;
    private LiveData<List<HighlightEntities>> allHighlights;

    public HighlightRepository(Context context) {
        highlightDoa = HighlightDatabase.getInstance(context).highlightDoa();
    }

    public List<HighlightEntities> getChapHighlights(int v, int b, int c) {
        chapHighLights = highlightDoa.getChapHighlights(v, b, c);
        return chapHighLights;
    }

    public int getHighlightExists(int z, int b, int c, int v) {
        return highlightDoa.getHighlightExists(z, b, c, v);
    }

    public LiveData<List<HighlightEntities>> getAllHighlights() {
        allHighlights = highlightDoa.getAllHighlights();
        return allHighlights;
    }

    public int deleteHighlight(int id) {
        return highlightDoa.deleteHighlight(id);
    }

    public int updateColor(int c, int id) {
        return highlightDoa.updateColor(c, id);
    }

    public long insertHighlight(HighlightEntities highlightEntities) {
        return highlightDoa.insertHighlight(highlightEntities);
    }

}
