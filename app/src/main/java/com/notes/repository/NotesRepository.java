package com.notes.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.notes.models.Note;
import com.notes.models.ResultCallback;
import com.notes.services.DataService;

public class NotesRepository {
    private LiveData<PagedList<Note>> pagedList;
    private LiveData<NotesDataSource> dataSource;

    public NotesRepository(DataService dataService) {
        NotesDataSourceFactory factory = new NotesDataSourceFactory(dataService);
        dataSource = factory.getCurrentDataSource();
        pagedList = new LivePagedListBuilder<>(factory, 20).build();
    }

    /**
     * Наблюдаемая версия помеченного списка заметок с учетом жизненного цикла.
     * Это используется для визуализации RecyclerView всех заметок.
     */
    public LiveData<PagedList<Note>> getPagedList() {
        return pagedList;
    }

    /**
     * Операция API для создания элемента в хранилище данных
     */
    public void create(String title, String content, ResultCallback<Note> callback) {
        dataSource.getValue().createItem(title, content, callback);
    }

    /**
     * Операция API для обновления элемента в хранилище данных
     */
    public void update(Note note, ResultCallback<Note> callback) {
        dataSource.getValue().updateItem(note, callback);
    }

    /**
     * Операция API для удаления элемента из хранилища данных
     */
    public void delete(String noteId, ResultCallback<Boolean> callback) {
        dataSource.getValue().deleteItem(noteId, callback);
    }

    /**
     * Операция API для получения элемента из хранилища данных
     */
    public void get(String noteId, ResultCallback<Note> callback) {
        dataSource.getValue().getItem(noteId, callback);
    }
}
