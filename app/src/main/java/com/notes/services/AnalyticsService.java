package com.notes.services;

import java.util.Map;

public interface AnalyticsService {
    /**
     * Записывает событие начала сеанса в analytics stream
     */
    void startSession();

    /**
     * Записывает событие остановки сеанса в analytics stream
     */
    void stopSession();

    /**
     * Запишите пользовательское событие в analytics stream
     *
     * eventName имя пользовательского события
     * attributes список пар ключ-значение для записи строковых атрибутов
     * metrics список пар ключ-значение для записи числовых метрик
     */
    void recordEvent(String eventName, Map<String,String> attributes, Map<String,Double> metrics);
}
