package com.notes.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.notes.R;
import com.notes.models.Note;

public class NoteListViewHolder extends RecyclerView.ViewHolder {
    private TextView titleField;
    private TextView idField;
    private Note note;

    public NoteListViewHolder(View view) {
        super(view);

        titleField = view.findViewById(R.id.list_title);
        idField = view.findViewById(R.id.list_id);
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
        String title = note.getTitle();
        titleField.setText(title == null ? "null" : title);
        idField.setText(note.getNoteId());
    }
}
