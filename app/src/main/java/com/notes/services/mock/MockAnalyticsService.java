package com.notes.services.mock;

import android.util.Log;

import com.notes.services.AnalyticsService;

import java.util.Locale;
import java.util.Map;

public class MockAnalyticsService implements AnalyticsService {
    private static final String TAG = "MockAnalyticsService";

    @Override
    public void startSession() {
        Log.v(TAG, "startSession()");
    }

    @Override
    public void stopSession() {
        Log.v(TAG, "stopSession()");

    }

    @Override
    public void recordEvent(String eventName, Map<String, String> attributes, Map<String, Double> metrics) {
        StringBuilder event = new StringBuilder("");
        if (attributes != null) {
            for (Map.Entry<String,String> entry : attributes.entrySet()) {
                event.append(String.format(Locale.US, ", %s=\"%s\"", entry.getKey(), entry.getValue()));
            }
        }
        if (metrics != null) {
            for (Map.Entry<String,Double> entry : metrics.entrySet()) {
                event.append(String.format(Locale.US, ", %s=%.2f", entry.getKey(), entry.getValue()));
            }
        }
        if (!event.toString().isEmpty()) {
            event.setCharAt(0, ':');
        }
        Log.v(TAG, String.format(Locale.US, "recordEvent(%s)%s", eventName, event.toString()));
    }
}
