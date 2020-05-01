package controller;

import javafx.util.Pair;

public class BoardPosition  {
    private Integer row, column;

    public BoardPosition(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (((BoardPosition)o).row != this.row){
            return false;
        }
        if (((BoardPosition)o).column != this.column){
            return false;
        }
        return true;
    }
}
