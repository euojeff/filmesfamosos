package com.jeffersonaraujo.filmesfamosos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jeffersonaraujo.filmesfamosos.database.AppDatabase;
import com.jeffersonaraujo.filmesfamosos.database.FilmeFavoritoEntry;

public class DetalheFilmeViewModel extends ViewModel {

    private LiveData<FilmeFavoritoEntry> filmeFavorito;

    public DetalheFilmeViewModel(AppDatabase database, String key) {
        filmeFavorito = database.filmeFavoritoDao().carregarFilmeFavorito(key);
    }

    public LiveData<FilmeFavoritoEntry> getFilmeFavorito() {
        return filmeFavorito;
    }
}
