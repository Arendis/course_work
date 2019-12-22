package com.notes.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.notes.Injection;
import com.notes.models.Note;
import com.notes.models.ResultCallback;
import com.notes.repository.NotesRepository;

public class NoteDetailViewModel extends ViewModel {
    private String noteId;
    private MutableLiveData<String> mTitle;
    private MutableLiveData<String> mContent;
    private NotesRepository notesRepository;

    public NoteDetailViewModel() {
        this.notesRepository = Injection.getNotesRepository();
        this.mTitle = new MutableLiveData<>();
        this.mContent = new MutableLiveData<>();
    }

    public void setNoteId(final String noteId) {
        this.noteId = noteId;
        notesRepository.get(noteId, (Note result) -> {
            if (result != null)
                mTitle.postValue(result.getTitle());
                mContent.postValue(result.getContent());
                this.noteId = result.getNoteId();
        });
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }

    public LiveData<String> getContent() {
        return mContent;
    }

    public synchronized void create(String title, String content, ResultCallback<Note> callback) {
        notesRepository.create(title, content, (Note result) -> {
            if (result != null) {
                noteId = result.getNoteId();
                callback.onResult(result);
            }
        });
    }

    public synchronized void update(String title, String content) {
        Note newNote = new Note(noteId, title, content);
        notesRepository.update(newNote, (Note result) -> {
            /* Do Nothing */
        });
    }
}
