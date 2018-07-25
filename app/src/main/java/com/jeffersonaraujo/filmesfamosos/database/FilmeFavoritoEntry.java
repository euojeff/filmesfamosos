package com.jeffersonaraujo.filmesfamosos.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "filme_favorito")
public class FilmeFavoritoEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String key;
    private String title;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public FilmeFavoritoEntry(String key, String title, Date updatedAt) {
        this.key = key;
        this.title = title;
        this.updatedAt = updatedAt;
    }

    public FilmeFavoritoEntry(int id, String key, String title, Date updatedAt) {
        this.id = id;
        this.key = key;
        this.title = title;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
