package com.jeffersonaraujo.filmesfamosos.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FilmeFavoritoDao {

    @Query("SELECT * FROM filme_favorito ORDER BY updated_at desc")
    LiveData<List<FilmeFavoritoEntry>> carregarFilmesFavoritos();

    @Insert
    void insertFilmeFavorito(FilmeFavoritoEntry filmeEntry);

    @Query("Delete FROM filme_favorito WHERE `key` = :key")
    void deleteFilmeFavorito(String key);

    @Query("SELECT * FROM filme_favorito WHERE `key` = :key")
    LiveData<FilmeFavoritoEntry> carregarFilmeFavorito(String key);
}
