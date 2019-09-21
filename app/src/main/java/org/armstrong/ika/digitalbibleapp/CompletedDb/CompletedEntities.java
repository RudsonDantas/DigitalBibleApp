package org.armstrong.ika.digitalbibleapp.CompletedDb;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "completed")
public class CompletedEntities {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "abbreviation")
    private String abbreviation;
    @ColumnInfo(name = "bookname")
    private String bookname;
    @ColumnInfo(name = "time")
    private String time;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getBookname() {
        return bookname;
    }
    public void setBookname(String bookname) { this.bookname = bookname; }

    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}

}
