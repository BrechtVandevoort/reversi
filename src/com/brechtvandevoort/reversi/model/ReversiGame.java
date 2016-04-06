package com.brechtvandevoort.reversi.model;

import sun.util.resources.ca.CalendarData_ca;

import java.util.Observable;

/**
 * Created by brecht on 05/04/2016.
 */
public class ReversiGame extends Observable {
    private ReversiBoard _board;
    private Player _activePlayer = Player.BLACK;

    public ReversiGame() {
        _board = new ReversiBoard();
        _board.initBoard();
    }

    public ReversiBoard getBoard() {
        return _board;
    }

    public Player getActivePlayer() {
        return _activePlayer;
    }

    public boolean place(Position pos) {
        if(_board.place(pos, _activePlayer)) {
            _activePlayer = (_activePlayer == Player.BLACK)? Player.WHITE : Player.BLACK;
            setChanged();
            notifyObservers();
            return true;
        }
        else {
            return false;
        }
    }
}
