package com.jeffersonaraujo.filmesfamosos;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jeffersonaraujo.filmesfamosos.database.AppDatabase;
import com.jeffersonaraujo.filmesfamosos.database.FilmeFavoritoEntry;

import java.util.List;

public class ListaFilmesViewModel extends AndroidViewModel {

    private LiveData<List<FilmeFavoritoEntry>> filmesFavoritos;

    public ListaFilmesViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        filmesFavoritos = database.filmeFavoritoDao().carregarFilmesFavoritos();
    }

    public LiveData<List<FilmeFavoritoEntry>> getFilmesFavoritos() {
        return filmesFavoritos;
    }
}
