package com.brechtvandevoort.reversi.model;

/**
 * Created by brecht on 06/04/2016.
 */
public class Score {
    private boolean _gameEnded;
    private int _numWhite;
    private int _numBlack;

    public Score(int numWhite, int numBlack, boolean gameEnded) {
        _numWhite = numWhite;
        _numBlack = numBlack;
        _gameEnded = gameEnded;
    }

    public boolean isGameEnded() {
        return _gameEnded;
    }

    public int getNumWhite() {
        return _numWhite;
    }

    public int getNumBlack() {
        return _numBlack;
    }

    public int getNum(PlayerType type) {
        if(type == PlayerType.BLACK)
            return _numBlack;
        else
            return _numWhite;
    }
}
