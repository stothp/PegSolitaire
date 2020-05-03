package controller;

import javafx.util.Pair;

public class BoardTypePair extends Pair<BoardType, String> {
    public BoardTypePair(BoardType key, String value) {
        super(key, value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
