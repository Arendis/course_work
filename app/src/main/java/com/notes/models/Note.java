package com.notes.models;

import java.util.UUID;

/**
 * Модель для одной заметки
 */
public class Note {
    private String noteId;
    private String title;
    private String content;

    public Note() {
        noteId = UUID.randomUUID().toString();
        title = "";
        content = "";
    }

    public Note(String noteId) {
        this.noteId = noteId;
        title = "";
        content = "";
    }

    public Note(String noteId, String title, String content) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
