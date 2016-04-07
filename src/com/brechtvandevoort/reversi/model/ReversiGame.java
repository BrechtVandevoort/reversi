package com.brechtvandevoort.reversi.model;

import com.brechtvandevoort.reversi.model.playerimplementations.HumanPlayer;
import com.brechtvandevoort.reversi.model.playerimplementations.RandomPlayer;
import com.brechtvandevoort.reversi.model.playerimplementations.RandomWinChancePlayer;

import java.util.Observable;

/**
 * Implementation of the ReversiGame.
 *
 * Created by brecht on 05/04/2016.
 */
public class ReversiGame extends Observable {
    private ReversiBoard _board;
    private PlayerType _activePlayerType;
    private Player _playerBlack;
    private Player _playerWhite;

    /**
     * Constructor
     */
    public ReversiGame() {
        _board = new ReversiBoard();
        _activePlayerType = PlayerType.BLACK;
        _playerBlack = new HumanPlayer();
        _playerWhite = new RandomWinChancePlayer();
        _board.initBoard();
    }

    /**
     * Initialises the game.
     */
    public void init() {
        _playerBlack.init(PlayerType.BLACK, this);
        _playerWhite.init(PlayerType.WHITE, this);
        _board.initBoard();
        _playerBlack.notifyForMove();
    }

    /**
     * Returns the ReversiBoard.
     * @return The ReversiBoard.
     */
    public ReversiBoard getBoard() {
        return _board;
    }

    /**
     * Returns the active player.
     * @return The PlayerType of the active player.
     */
    public PlayerType getActivePlayerType() {
        return _activePlayerType;
    }

    /**
     * Returns the active player.
     * @return The Player implementation of the active player.
     */
    public Player getActivePlayer() {
        if(_activePlayerType == PlayerType.BLACK)
            return _playerBlack;
        else
            return _playerWhite;
    }

    /**
     * Places a stone on the board.
     * @param pos The Position to place the stone on.
     * @param player The Player placing the stone.
     * @return True if accepted, false if not accepted.
     */
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
