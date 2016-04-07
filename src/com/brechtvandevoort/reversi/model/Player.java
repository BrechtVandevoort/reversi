package com.brechtvandevoort.reversi.model;

/**
 * Created by brecht on 07/04/2016.
 */
public interface Player {

    /**
     * Initialises the player.
     * @param type The PlayerType
     * @param game The ReversiGame
     * @return True if initialisation was successful, false if not.
     */
    boolean init(PlayerType type, ReversiGame game);

    /**
     * This method needs to be called when the player should do a move.
     */
    void notifyForMove();

    /**
     * Propose a move to the player. The player is not guaranteed to do this given move.
     * Used when dealing with a human player.
     * @param pos The position to place the new stone.
     */
    void proposeMove(Position pos);

    /**
     * Returns the PlayerType of this player.
     * @return The PlayerType.
     */
    PlayerType getPlayerType();

}
