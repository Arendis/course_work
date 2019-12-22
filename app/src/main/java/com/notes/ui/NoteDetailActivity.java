package com.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.notes.Injection;
import com.notes.NotesApp;
import com.notes.R;
import com.notes.services.AnalyticsService;

public class NoteDetailActivity extends AppCompatActivity {
    /**
     * Инъекция AnalyticsService
     */
    private AnalyticsService analyticsService = Injection.getAnalyticsService();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        // Настройка панели действий так, чтобы она имела кнопку «Назад»
        Toolbar detail_toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(detail_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Загрузить фрагмент
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString(NotesApp.ITEM_ID, getIntent().getStringExtra(NotesApp.ITEM_ID));
        fragment.setArguments(arguments);

        // Добавьте фрагмент в пользовательский интерфейс
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.note_detail_container, fragment)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsService.recordEvent("NoteDetailActivity", null, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, NoteListActivity.class);
                navigateUpTo(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
