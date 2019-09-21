package org.armstrong.ika.digitalbibleapp.BookmarkDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class BookmarkEntities {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "language")
    private String language;
    @ColumnInfo(name = "abbreviation")
    private String abbreviation;
    @ColumnInfo(name = "version")
    private int version;
    @ColumnInfo(name = "book")
    private int book;
    @ColumnInfo(name = "book_name")
    private String book_name;
    @ColumnInfo(name = "chapter")
    private int chapter;
    @ColumnInfo(name = "verse")
    private int verse;
    @ColumnInfo(name = "text")
    private String text;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getLanguage() { return language;}
    public void  setLanguage( String language) {this.language = language; }

    public String getAbbreviation() { return abbreviation;}
    public void setAbbreviation( String abbreviation) {this.abbreviation = abbreviation;}

    public int getVersion() {return version;}
    public void setVersion( int version) {this.version = version;}

    public int getBook() {return book;}
    public void setBook( int book) {this.book = book;}

    public String getBook_name() {return book_name;}
    public void setBook_name( String book_name) {this.book_name = book_name;}

    public int getChapter() {return chapter;}
    public void setChapter(int chapter) {this.chapter = chapter;}

    public int getVerse() {return verse;}
    public void setVerse(int verse) {this.verse = verse;}

    public String getText() {return text;}
    public void setText( String text) {this.text = text;}
}
