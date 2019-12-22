package com.notes.services.mock;

import com.notes.models.Note;
import com.notes.models.PagedListConnectionResponse;
import com.notes.models.ResultCallback;
import com.notes.services.DataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * создаем пример заметки при в ходе в приложение
 */
public class MockDataService implements DataService {
    private ArrayList<Note> items;

    public MockDataService() {
        items = new ArrayList<>();
        Note item = new Note();
        item.setTitle(String.format(Locale.US, "Пример заметки"));
        item.setContent(String.format(Locale.US, "Содержимое заметки"));
        items.add(item);
    //}
    }

    /**
     * Имитация вызова API для сетевого сервиса, который возвращает выгружаемые данные.
     */
    @Override
    public void loadNotes(int limit, String after, ResultCallback<PagedListConnectionResponse<Note>> callback) {
        if (limit < 1 || limit > 100) throw new IllegalArgumentException("Limit must be between 1 and 100");

        int firstItem = 0;
        if (after != null) {
            firstItem = indexOfFirst(after);
            if (firstItem < 0) {
                callback.onResult(new PagedListConnectionResponse<>(Collections.<Note>emptyList(), null));
                return;
            }
            firstItem++;
        }
        if (firstItem > items.size() - 1) {
            callback.onResult(new PagedListConnectionResponse<>(Collections.<Note>emptyList(), null));
            return;
        }
        int nItems = Math.min(limit, items.size() - firstItem);
        if (nItems == 0) {
            callback.onResult(new PagedListConnectionResponse<>(Collections.<Note>emptyList(), null));
            return;
        }

        List<Note> sublist = new ArrayList<>(items.subList(firstItem, firstItem + nItems));
        String nextToken = (firstItem + nItems - 1 == items.size()) ? null : sublist.get(sublist.size() - 1).getNoteId();
        callback.onResult(new PagedListConnectionResponse<>(sublist, nextToken));
    }

    /**
     * Загрузить одну заметку из текущего списка заметок
     */
    @Override
    public void getNote(String noteId, ResultCallback<Note> callback) {
        if (noteId == null || noteId.isEmpty()) throw new IllegalArgumentException();

        int idx = indexOfFirst(noteId);
        callback.onResult(idx >= 0 ? items.get(idx) : null);
    }

    /**
     * Создать новую заметку заметку в резервном хранилище
     */
    @Override
    public void createNote(String title, String content, ResultCallback<Note> callback) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        items.add(note);
        callback.onResult(note);
    }

    /**
     * Обновление существующей заметки в резервном хранилище
     */
    @Override
    public void updateNote(Note note, ResultCallback<Note> callback) {
        int idx = indexOfFirst(note.getNoteId());
        if (idx >= 0) {
            items.set(idx, note);
            callback.onResult(note);
        } else {
            callback.onResult(null);
        }
    }

    /**
     * Удаление заметки из резервного хранилища
     */
    @Override
    public void deleteNote(String noteId, ResultCallback<Boolean> callback) {
        if (noteId == null || noteId.isEmpty()) throw new IllegalArgumentException();

        int idx = indexOfFirst(noteId);
        if (idx >= 0) items.remove(idx);
        callback.onResult(idx >= 0);
    }

    /**
     * Возвращает индекс первой заметки, которая соответствует
     * индекс note, или -1 если не найден
     */
    private int indexOfFirst(String noteId) {
        if (items.isEmpty()) throw new IndexOutOfBoundsException();
        for (int i = 0 ; i < items.size() ; i++) {
            if (items.get(i).getNoteId().equals(noteId))
                return i;
        }
        return -1;
    }
}
