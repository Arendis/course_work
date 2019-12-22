package com.notes;

import android.content.Context;

import com.notes.repository.NotesRepository;
import com.notes.services.AnalyticsService;
import com.notes.services.DataService;
import com.notes.services.mock.MockAnalyticsService;
import com.notes.services.mock.MockDataService;

/**
 * Это «поддельная» система внедрения зависимостей.
 */
public class Injection {
    private static DataService dataService = null;
    private static AnalyticsService analyticsService = null;
    private static NotesRepository notesRepository = null;

    public static synchronized DataService getDataService() {
        return dataService;
    }

    public static synchronized AnalyticsService getAnalyticsService() {
        return analyticsService;
    }

    public static synchronized NotesRepository getNotesRepository() {
        return notesRepository;
    }

    public static synchronized void initialize(Context context) {
        if (analyticsService == null) {
            analyticsService = new MockAnalyticsService();
        }

        if (dataService == null) {
            dataService = new MockDataService();
        }

        if (notesRepository == null) {
            notesRepository = new NotesRepository(dataService);
        }
    }
}
