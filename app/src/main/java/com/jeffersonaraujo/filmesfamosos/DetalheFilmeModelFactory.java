package com.jeffersonaraujo.filmesfamosos;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jeffersonaraujo.filmesfamosos.database.AppDatabase;

public class DetalheFilmeModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDataBase;
    private final String mKey;

    public DetalheFilmeModelFactory(AppDatabase database, String key) {
        mDataBase = database;
        mKey = key;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetalheFilmeViewModel(mDataBase, mKey);
    }
}
