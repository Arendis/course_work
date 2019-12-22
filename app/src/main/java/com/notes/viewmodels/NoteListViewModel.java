package com.notes.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.notes.Injection;
import com.notes.models.Note;
import com.notes.models.ResultCallback;
import com.notes.repository.NotesRepository;

public class NoteListViewModel extends ViewModel {
    private NotesRepository notesRepository;

    public NoteListViewModel() {
        this.notesRepository = Injection.getNotesRepository();
    }

    public LiveData<PagedList<Note>> getNotesList() {
        return notesRepository.getPagedList();
    }

    public void removeNote(String noteId) {
        notesRepository.delete(noteId, new ResultCallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                /* Do nothing */
            }
        });
    }
}
