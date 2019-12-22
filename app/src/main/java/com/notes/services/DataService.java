package com.notes.services;

import com.notes.models.Note;
import com.notes.models.PagedListConnectionResponse;
import com.notes.models.ResultCallback;

public interface DataService {
    /**
     * Загрузить одну страницу заметок.
     *
     * limit запрошенное количество items
     * after  "next token" от предыдущего вызова
     * callback ответ от сервера
     */
    void loadNotes(int limit, String after, ResultCallback<PagedListConnectionResponse<Note>> callback);

    /**
     * Загрузить одну заметку
     *
     * noteId идентификатор запроса
     */
    void getNote(String noteId, ResultCallback<Note> callback);

    /**
     * Создать новую заметку заметку в  backing store
     *
     * title название новой заметки
     * content содержание для новой заметки
     * callback ответ от сервера (ноль будет означать, что операция не удалась)
     */
    void createNote(String title, String content, ResultCallback<Note> callback);

    /**
     * Обновите существующую заметку в резервном хранилище
     *
     * note новое содержание заметки
     */
    void updateNote(Note note, ResultCallback<Note> callback);

    /**
     * Удалить заметку из backing store
     *
     * noteId идентификатор заметки, которая будет удалена
     * callback ответ от сервера (Boolean = true указывает на успех)
     */
    void deleteNote(String noteId, ResultCallback<Boolean> callback);

}
