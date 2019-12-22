package com.notes.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.notes.Injection;
import com.notes.NotesApp;
import com.notes.R;
import com.notes.models.Note;
import com.notes.services.AnalyticsService;
import com.notes.viewmodels.NoteListViewModel;

import java.util.HashMap;

public class NoteListActivity extends AppCompatActivity {
    /**
     Если устройство работает в двухпанельном режиме, для него установлено значение true.
     В двухпанельном режиме пользовательский интерфейс располагается рядом друг с другом,
     список слева и подробности справа. В режиме одной панели список и детали - это отдельные страницы.
     */
    private boolean twoPane = false;

    /**
     * Модель представления
     */
    private NoteListViewModel viewModel;

    /**
     * The analytics service
     */
    private AnalyticsService analyticsService = Injection.getAnalyticsService();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel.class);
        setContentView(R.layout.activity_note_list);
        if (findViewById(R.id.note_detail_container) != null) twoPane = true;

        // Настройка панели действий
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());

        // Добавка обработчика щелчка элемента к плавающей кнопке действия для добавления заметки
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((View v) -> loadNoteDetailFragment("new"));

        // Создание адаптера, который будет использоваться для загрузки элементов в представление переработчика
        final NoteListAdapter adapter = new NoteListAdapter((Note item) -> loadNoteDetailFragment(item.getNoteId()));

        // Создать обработчик смахивания для удаления
        SwipeToDelete swipeHandler = new SwipeToDelete(this, (Note item) -> viewModel.removeNote(item.getNoteId()));
        ItemTouchHelper swipeToDelete = new ItemTouchHelper(swipeHandler);

        // Настройка список заметок
        RecyclerView note_list = findViewById(R.id.note_list);
        swipeToDelete.attachToRecyclerView(note_list);
        note_list.setAdapter(adapter);

        // Убедитесь, что список заметок обновляется при каждом обновлении хранилища.
        viewModel.getNotesList().observe(this, adapter::submitList);
    }

    @Override
    public void onResume() {
        super.onResume();
        HashMap<String,String> attributes = new HashMap<>();
        attributes.put("twoPane", twoPane ? "true" : "false");
        analyticsService.recordEvent("NoteListActivity", attributes, null);
    }

    /**
     * Загружает детали заметки правильным образом, в зависимости от того, является ли это двухпанельным режимом.
     *
     * noteId идентификатор заметки для загрузки
     */
    private void loadNoteDetailFragment(String noteId) {
        if (twoPane) {
            Fragment fragment = new NoteDetailFragment();
            Bundle arguments = new Bundle();
            arguments.putString(NotesApp.ITEM_ID, noteId);
            fragment.setArguments(arguments);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.note_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra(NotesApp.ITEM_ID, noteId);
            startActivity(intent);
        }
    }
}
