package com.brechtvandevoort.reversi.model;

/**
 * Created by brecht on 06/04/2016.
 */
public class Position {
    private int _row;
    private int _col;

    @Override
    public String toString() {
        return "Position{" +
                "_row=" + _row +
                ", _col=" + _col +
                '}';
    }

    public Position(int row, int col) {
        _row = row;
        _col = col;
    }

    public int getRow() {
        return _row;
    }

    public int getCol() {
        return _col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return _col == position._col && _row == position._row;
    }

    @Override
    public int hashCode() {
        int result = _row;
        result = 31 * result + _col;
        return result;
    }
}
