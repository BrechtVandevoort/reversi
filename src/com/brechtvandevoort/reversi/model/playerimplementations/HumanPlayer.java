package com.brechtvandevoort.reversi.model.playerimplementations;

import com.brechtvandevoort.reversi.model.Player;
import com.brechtvandevoort.reversi.model.PlayerType;
import com.brechtvandevoort.reversi.model.Position;
import com.brechtvandevoort.reversi.model.ReversiGame;

/**
 * Created by brecht on 07/04/2016.
 */
public class HumanPlayer implements Player {
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
        //Ignore
    }

    /**
     * Propose a move to the player. The player is not guaranteed to do this given move.
     * Used when dealing with a human player.
     *
     * @param pos The position to place the new stone.
     */
    @Override
    public void proposeMove(Position pos) {
        _game.place(pos, this);
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
