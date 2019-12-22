package com.notes.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.notes.NotesApp;
import com.notes.R;
import com.notes.models.Note;
import com.notes.viewmodels.NoteDetailViewModel;

public class NoteDetailFragment extends Fragment {
    NoteDetailViewModel viewModel;
    String noteId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String noteId = arguments.getString(NotesApp.ITEM_ID);
            this.noteId = (noteId.equals("new")) ? null : noteId;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_detail, container, false);

        final EditText titleField = view.findViewById(R.id.edit_title);
        titleField.setEnabled(false);   // Disable the field by default

        final EditText contentField = view.findViewById(R.id.edit_content);
        contentField.setEnabled(false);

        viewModel = ViewModelProviders.of(this).get(NoteDetailViewModel.class);
        // Как только мы получим значение, включите поле.
        viewModel.getTitle().observe(this, (String title) -> {
            titleField.setText(title);
            titleField.setEnabled(true);
        });
        viewModel.getContent().observe(this, (String content) -> {
            contentField.setText(content);
            contentField.setEnabled(true);
        });

        // Если это новая заметка, создайте заметку, затем включите поля.
        //В противном случае просто загрузите поля - поля получены через наблюдаемые
        if (noteId == null) {
            viewModel.create("", "", (Note result) -> {
                titleField.setEnabled(true);
                contentField.setEnabled(true);
            });
        } else {
            viewModel.setNoteId(noteId);
        }

        TextWatcher saveHandler = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) {
                String title = titleField.getText().toString();
                String content = contentField.getText().toString();
                viewModel.update(title, content);
            }
        };

        titleField.addTextChangedListener(saveHandler);
        contentField.addTextChangedListener(saveHandler);

        return view;
    }
}
