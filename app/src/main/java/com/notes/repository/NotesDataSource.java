package com.notes.repository;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.notes.models.Note;
import com.notes.models.PagedListConnectionResponse;
import com.notes.models.ResultCallback;
import com.notes.services.DataService;

import javax.xml.transform.Result;

public class NotesDataSource extends PageKeyedDataSource<String,Note> {
    private static final String TAG = "NotesDataSource";
    private DataService dataService;

    NotesDataSource(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * Часть PageKeyedDataSource - загрузить первую страницу списка.
     * Вызывается, когда источник данных впервые создается или становится недействительным
     */
    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Note> callback) {
        Log.d(TAG, String.format("loadInitial(%d)", params.requestedLoadSize));
        dataService.loadNotes(params.requestedLoadSize, null, (PagedListConnectionResponse<Note> result) -> {
            callback.onResult(result.getItems(), null, result.getNextToken());
        });
    }

    /**
     Часть PageKeyedDataSource - загрузить следующую страницу списка.
     Это вызывается после того, как loadInitial () вернула ответ,
     который включает nextToken для загрузки следующей страницы элементов.
     */
    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, Note> callback) {
        Log.d(TAG, String.format("loadAfter(%d, %s)", params.requestedLoadSize, params.key));
        dataService.loadNotes(params.requestedLoadSize, params.key, (PagedListConnectionResponse<Note> result) -> {
            callback.onResult(result.getItems(), result.getNextToken());
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Note> callback) {
        Log.d(TAG, String.format("loadBefore(%d, %s)", params.requestedLoadSize, params.key));
        invalidate();
    }

    /**
     * Хранилище заметок также должно выполнять операции с нестраничным списком.
     * Эти операции передаются непосредственно службе данных.
     * Тем не менее, удаления (и сохранения) влияют на список, поэтому мы должны убедиться,
     * что список будет признан недействительным в случае успеха. Этот вызов для удаления элемента.
     */
    public void deleteItem(String noteId, @NonNull final ResultCallback<Boolean> callback) {
        dataService.deleteNote(noteId, (Boolean result) -> {
            if (result) invalidate();
            callback.onResult(result);
        });
    }

    /**
     * получить один элемент из data service.
     */
    public void getItem(String noteId, @NonNull ResultCallback<Note> callback) {
        dataService.getNote(noteId, callback);
    }

    /**
     * Хранилище заметок также должно выполнять операции с нестраничным списком.
     * Эти операции передаются непосредственно службе данных.
     * Тем не менее, удаления (и сохранения) влияют на список, поэтому мы должны убедиться,
     * что список будет признан недействительным в случае успеха. Этот призыв сохранить элемент.
     */
    public void createItem(@NonNull String title, @NonNull String content, @NonNull final ResultCallback<Note> callback) {
        dataService.createNote(title, content, (Note result) -> {
            if (result != null) invalidate();
            callback.onResult(result);
        });
    }

    public void updateItem(@NonNull Note note, @NonNull final ResultCallback<Note> callback) {
        dataService.updateNote(note, (Note result) -> {
            if (result != null) invalidate();
            callback.onResult(result);
        });
    }
}
