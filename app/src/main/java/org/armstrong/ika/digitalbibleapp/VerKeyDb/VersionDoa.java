package org.armstrong.ika.digitalbibleapp.VerKeyDb;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface VersionDoa {

    @Query("SELECT * FROM version_key WHERE active=1")
    List<VersionEntities> listActiveVersions();

    @Query("SELECT count(*) FROM version_key WHERE active=1 AND copyRight=1")
    int countActiveVersions();

    @Query("UPDATE version_key SET active=:act WHERE number=:num")
    void setVersionActive(int act, int num);

    @Query("SELECT transLang FROM version_key WHERE number=:number AND copyRight=1")
    String getLangFromNumber(int number);

    @Query("SELECT * FROM version_key WHERE copyRight=1 ORDER BY number ASC")
    List<VersionEntities> getAllVersions();

    @Query("SELECT * FROM version_key WHERE active=1 AND number!=:number AND copyRight=1 ORDER BY number ASC")
    LiveData<List<VersionEntities>> getActiveVersions(int number);

    @Query("SELECT * FROM version_key WHERE active=1 AND number!=:number AND copyRight=1 ORDER BY number ASC")
    List<VersionEntities> getCompareVersions(int number);

    @Query("UPDATE version_key SET copyRight=1")
    void unlockVersions();

    @Query("SELECT verAbbr FROM version_key WHERE number=:number")
    String getAbbrFromNumber(int number);


}