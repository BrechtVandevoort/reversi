package com.brechtvandevoort.reversi.model;

import com.brechtvandevoort.reversi.model.playerimplementations.HumanPlayer;
import com.brechtvandevoort.reversi.model.playerimplementations.RandomPlayer;
import com.brechtvandevoort.reversi.model.playerimplementations.RandomWinChancePlayer;

import java.util.Observable;

/**
 * Created by brecht on 05/04/2016.
 */
public class ReversiGame extends Observable {
    private ReversiBoard _board;
    private PlayerType _activePlayerType;
    private Player _playerBlack;
    private Player _playerWhite;

    public ReversiGame() {
        _board = new ReversiBoard();
        _activePlayerType = PlayerType.BLACK;
        _playerBlack = new HumanPlayer();
        _playerWhite = new RandomWinChancePlayer();
        _board.initBoard();
    }

    public void init() {
        _playerBlack.init(PlayerType.BLACK, this);
        _playerWhite.init(PlayerType.WHITE, this);
        _board.initBoard();
        _playerBlack.notifyForMove();
    }

    public ReversiBoard getBoard() {
        return _board;
    }

    public PlayerType getActivePlayerType() {
        return _activePlayerType;
    }

    public Player getActivePlayer() {
        if(_activePlayerType == PlayerType.BLACK)
            return _playerBlack;
        else
            return _playerWhite;
    }

    public boolean place(Position pos, Player player) {
        if(_activePlayerType != player.getPlayerType()) {
            return false;
        }

        if(_board.place(pos, _activePlayerType)) {
            switchPlayer();
            setChanged();
            notifyObservers();
            return true;
        }
        else {
            return false;
        }
    }

    private void switchPlayer() {
        PlayerType otherPlayer = (_activePlayerType == PlayerType.BLACK)? PlayerType.WHITE : PlayerType.BLACK;
        if (_board.canPlace(otherPlayer) || !_board.canPlace(_activePlayerType)) {
            _activePlayerType = otherPlayer;
        }
        if(!_board.isGameEnded())
            getActivePlayer().notifyForMove();
    }
}
