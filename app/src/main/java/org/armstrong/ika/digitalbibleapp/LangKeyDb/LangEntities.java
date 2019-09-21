package org.armstrong.ika.digitalbibleapp.LangKeyDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "language_key")
public class LangEntities {

    @PrimaryKey
    private int id;  // no auto increment
    @ColumnInfo(name = "number") // 1
    private int number;
    @ColumnInfo(name = "code") // eng
    private String code;
    @ColumnInfo(name = "name") // Genesis
    private String name;

    public LangEntities() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}