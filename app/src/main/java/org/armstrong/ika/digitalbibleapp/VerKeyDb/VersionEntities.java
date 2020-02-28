package org.armstrong.ika.digitalbibleapp.VerKeyDb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// version_key

@Entity(tableName = "version_key")
public class VersionEntities {

    @PrimaryKey
    int id;

    @ColumnInfo(name = "number") // version number
    @NonNull
    private int number;

    @ColumnInfo(name = "active") // active or not active
    @NonNull
    private int active;

    @ColumnInfo(name = "copyRight") // copyright 1=no, 0=yes
    @NonNull
    private int copyRight;

    @ColumnInfo(name = "verAbbr") // Version Abbreviation
    @NonNull
    private String verAbbr;

    @ColumnInfo(name = "transLang") // language of translation
    @NonNull
    private String transLang;

    @ColumnInfo(name = "verName") // Version name
    @NonNull
    private String verName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {return number;}
    public void setNumber(int number) {this.number = number;}

    public int getActive() {return active;}
    public void setActive(int active) {this.active = active;}

    public int getCopyRight() {return copyRight;}
    public void setCopyRight(int copyRight) {this.copyRight = copyRight;}

    public String getVerAbbr() {return verAbbr;}
    public void setVerAbbr(String verAbbr) {this.verAbbr = verAbbr;}

    public String getTransLang() {return transLang;}
    public void setTransLang(String transLang) {this.transLang = transLang;}

    public String getVerName() {return verName;}
    public void setVerName(String verName) {this.verName = verName;}

}
