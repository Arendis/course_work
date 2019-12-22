package com.notes;

import android.app.Application;

/**
 * Класс приложения, отвечающий за инициализацию одиночек (singleton) и других общих компонентов
 */
public class NotesApp extends Application {
    /**
     * Имя поля, используемого для хранения идентификатора заметки,
     * когда информация передается внутри между действиями.
     */
    public static String ITEM_ID = "noteId";

    @Override
    public void onCreate() {
        super.onCreate();
        Injection.initialize(getApplicationContext());
    }
}

