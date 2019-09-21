package org.armstrong.ika.digitalbibleapp.BiblesDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Entity(tableName = "t_bible")
public class BiblesEntities {

    @PrimaryKey
    private int b; // book number
    @ColumnInfo(name = "c") // chapter
    private int c;
    @ColumnInfo(name = "v") // verse
    private int v;
    @ColumnInfo(name = "t") // text
    private String t;

    public int getB() {
        return b;
    }
    public void setB(int b) {
        this.b = b;
    }

    public int getC() {return c;}
    public void setC(int c) {this.c = c;}

    public int getV() {return v;}
    public void setV(int v) {this.v = v;}

    public String getT() {return t;}
    public void setT(String t) {this.t = t;}

}
