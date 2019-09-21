package org.armstrong.ika.digitalbibleapp.VerKeyDb;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VersionRepository {

    protected VersionDoa versionDoa;

    private LiveData<List<VersionEntities>> allActiveVersions;
    private List<VersionEntities> allCompareVersions, allVersions;

    public VersionRepository(Context context) {
        versionDoa = VersionDatabase.getInstance(context).versionDoa();
    }

    public List<VersionEntities> listActiveVersions() {
        return versionDoa.listActiveVersions();
    }

    public int countActiveVersions() {
        return versionDoa.countActiveVersions();
    }

    public void setVersionActive(final int act, final int num) {
        versionDoa.setVersionActive(act, num);
    }

    public void unlockVersions() {
        versionDoa.unlockVersions();
    }

    public String getAbbreviation(int num) {
        return versionDoa.getAbbrFromNumber(num);
    }

    public String getLangFromNumber(int num) {
        return versionDoa.getLangFromNumber(num);
    }

    public LiveData<List<VersionEntities>> getActiveVersions(int number) {
        allActiveVersions = versionDoa.getActiveVersions(number);
        return allActiveVersions;
    }

    public List<VersionEntities> getCompareVersions(int number) {
        allCompareVersions = versionDoa.getCompareVersions(number);
        return allCompareVersions;
    }

    public List<VersionEntities> getAllVersions() {
        allVersions = versionDoa.getAllVersions();
        return allVersions;
    }


}
