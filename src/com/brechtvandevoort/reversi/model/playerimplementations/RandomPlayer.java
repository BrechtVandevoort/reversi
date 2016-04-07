package com.brechtvandevoort.reversi.model.playerimplementations;

import com.brechtvandevoort.reversi.model.Player;
import com.brechtvandevoort.reversi.model.PlayerType;
import com.brechtvandevoort.reversi.model.Position;
import com.brechtvandevoort.reversi.model.ReversiGame;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by brecht on 07/04/2016.
 */
public class RandomPlayer implements Player {

    private PlayerType _type;
    private ReversiGame _game;

    /**
     * Initialises the player.
     *
     * @param type The PlayerType
     * @param game The ReversiGame
     * @return True if initialisation was successful, false if not.
     */
    @Override
    public boolean init(PlayerType type, ReversiGame game) {
        _type = type;
        _game = game;
        return true;
    }

    /**
     * This method needs to be called when the player should do a move.
     */
    @Override
    public void notifyForMove() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Position> positions = _game.getBoard().getPossiblePositions(_game.getActivePlayerType());
            Random random = new Random();
            Position randomPos = positions.get(random.nextInt(positions.size()));
            _game.place(randomPos, this);
        });
        t.start();
    }

    /**
     * Propose a move to the player. The player is not guaranteed to do this given move.
     * Used when dealing with a human player.
     *
     * @param pos The position to place the new stone.
     */
    @Override
    public void proposeMove(Position pos) {
        //Ignore
    }

    /**
     * Returns the PlayerType of this player.
     *
     * @return The PlayerType.
     */
    @Override
    public PlayerType getPlayerType() {
        return _type;
    }
}
