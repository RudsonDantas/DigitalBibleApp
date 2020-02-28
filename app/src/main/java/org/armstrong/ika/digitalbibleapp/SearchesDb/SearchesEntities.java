package org.armstrong.ika.digitalbibleapp.SearchesDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search_history")
public class SearchesEntities {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date") // date as integer
    private int date;

    @ColumnInfo(name = "version") // bible version
    private int version;

    @ColumnInfo(name = "text") // search field text
    private String text;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getDate() {return date;}
    public void setDate(int date) {this.date = date;}

    public int getVersion() {return version;}
    public void setVersion(int version) {this.version = version;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}


}
