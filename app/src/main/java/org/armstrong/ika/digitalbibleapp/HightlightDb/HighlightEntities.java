package org.armstrong.ika.digitalbibleapp.HightlightDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "highlights")
public class HighlightEntities {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "language")
    private String language;
    @ColumnInfo(name = "abbreviation")
    private String abbreviation;
    @ColumnInfo(name = "version")
    private int version;
    @ColumnInfo(name = "bookname")
    private String bookname;
    @ColumnInfo(name = "book")
    private int book;
    @ColumnInfo(name = "chapter")
    private int chapter;
    @ColumnInfo(name = "verse")
    private int verse;
    @ColumnInfo(name = "text")
    private String text;
    @ColumnInfo(name = "color")
    private int color;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public String getBookname() {
        return bookname;
    }
    public void setBookname(String bookname) { this.bookname = bookname; }

    public int getBook() {
        return book;
    }
    public void setBook(int book) {
        this.book = book;
    }

    public int getChapter() {
        return chapter;
    }
    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getVerse() {
        return verse;
    }
    public void setVerse(int verse) {
        this.verse = verse;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

}
