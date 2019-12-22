package com.notes.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.notes.models.Note;
import com.notes.services.DataService;

/**
 * Factory для создания data sources.  Когда NotesDataSource признан недействительным
 * (из-за обратной подкачки страниц или из-за того, что список был изменен),
 * мы должны создать новый источник данных.
 */
public class NotesDataSourceFactory extends DataSource.Factory<String, Note> {
    private DataService dataService;
    private MutableLiveData<NotesDataSource> mDataSource;
    private LiveData<NotesDataSource> currentDataSource;

    NotesDataSourceFactory(DataService dataService) {
        this.dataService = dataService;
        mDataSource = new MutableLiveData<>();
        currentDataSource = mDataSource;
    }

    public LiveData<NotesDataSource> getCurrentDataSource() {
        return currentDataSource;
    }

    @Override
    public DataSource<String, Note> create() {
        NotesDataSource dataSource = new NotesDataSource(dataService);
        mDataSource.postValue(dataSource);
        return dataSource;
    }
}
