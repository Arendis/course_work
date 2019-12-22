package com.notes.models;

import java.util.List;

public class PagedListConnectionResponse<T> {
    private List<T> items;
    private String nextToken;

    public PagedListConnectionResponse(List<T> items, String nextToken) {
        this.items = items;
        this.nextToken = nextToken;
    }

    public List<T> getItems() {
        return items;
    }

    public String getNextToken() {
        return nextToken;
    }
}
