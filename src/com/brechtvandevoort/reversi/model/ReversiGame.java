package com.brechtvandevoort.reversi.model;

import sun.util.resources.ca.CalendarData_ca;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ThreadFactory;

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
        if(_activePlayer != Player.BLACK) {
            return false;
        }
        return doPlace(pos);
    }

    public boolean isGameEnded() {
        return _board.getPossiblePositions(Player.BLACK).isEmpty() &&
                _board.getPossiblePositions(Player.WHITE).isEmpty();
    }

    public Score getScore() {
        return new Score(_board.countStones(Player.WHITE), _board.countStones(Player.BLACK), isGameEnded());
    }

    private void switchPlayer() {
        Player otherPlayer = (_activePlayer == Player.BLACK)? Player.WHITE : Player.BLACK;
        if (_board.canPlace(otherPlayer) || !_board.canPlace(_activePlayer))
            _activePlayer = otherPlayer;
    }

    private boolean doPlace(Position pos) {
        if(_board.place(pos, _activePlayer)) {
            switchPlayer();
            setChanged();
            notifyObservers();
            if(getActivePlayer() == Player.WHITE && !isGameEnded()) {
                computerMove();
            }
            return true;
        }
        else {
            return false;
        }
    }

    private void computerMove() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Position> positions = _board.getPossiblePositions(_activePlayer);
            Random r = new Random();
            doPlace(positions.get(r.nextInt(positions.size())));
        });
        t.start();
    }
}
