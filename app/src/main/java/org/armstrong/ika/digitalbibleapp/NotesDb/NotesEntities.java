package org.armstrong.ika.digitalbibleapp.NotesDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NotesEntities {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "ref")
    private String ref;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "version")
    private int version;
    @ColumnInfo(name = "book")
    private int book;
    @ColumnInfo(name = "chapter")
    private int chapter;
    @ColumnInfo(name = "verse")
    private int verse;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getRef() {return ref;}
    public void setRef(String ref) {this.ref = ref;}

    public String getDate() {return date;}
    public void setDate(String date){this.date = date;}

    public String getText() {return text;}
    public void setText(String text){this.text = text;}

    public int getVersion() {return version;}
    public void setVersion(int version) {this.version = version;}

    public int getBook() {return book;}
    public void setBook(int book) {this.book = book;}

    public int getChapter() {return chapter;}
    public void setChapter(int chapter) {this.chapter = chapter;}

    public int getVerse() {return verse;}
    public void setVerse(int verse) {this.verse = verse;}


}

