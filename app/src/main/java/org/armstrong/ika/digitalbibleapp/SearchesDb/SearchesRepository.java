package org.armstrong.ika.digitalbibleapp.SearchesDb;

import android.content.Context;

import java.util.List;

public class SearchesRepository {

    protected SearchesDoa searchesDoa;

    private List<SearchesEntities> searchHistory;

    public SearchesRepository(Context context) {
        searchesDoa = SearchesDatabase.getInstance(context).searchesDoa();
    }

    public void deleteSearchesByDate(int offset) {
        searchesDoa.deleteSearchesByDate(offset);
    }

    public List<SearchesEntities> getSearchHistory(int version) {
        return searchHistory = searchesDoa.getSearchHistory(version);
    }

    public boolean entryExits(String query, int version) {
        return searchesDoa.entryExits(query, version);
    }

    public void saveSearchHistory(SearchesEntities searchesEntities) {
        searchesDoa.saveSearchHistory(searchesEntities);
    }
}
