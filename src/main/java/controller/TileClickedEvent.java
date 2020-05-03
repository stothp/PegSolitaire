package controller;

import javafx.event.Event;
import javafx.event.EventType;

public class TileClickedEvent extends Event {
    public static final EventType<TileClickedEvent> TILE_CLICKED_EVENT_TYPE = new EventType(ANY);
    private Integer row, column;

    public TileClickedEvent(Integer row, Integer column) {
        super(TILE_CLICKED_EVENT_TYPE);
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }
}