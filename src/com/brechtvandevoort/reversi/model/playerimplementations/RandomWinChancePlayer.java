package com.brechtvandevoort.reversi.model.playerimplementations;

import com.brechtvandevoort.reversi.model.*;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by brecht on 07/04/2016.
 */
public class RandomWinChancePlayer implements Player {

    public static final int NUM_RUNS = 100;

    private ReversiGame _game;
    private PlayerType _type;
    private Random _random;

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
        _random = new Random();
        return true;
    }

    /**
     * This method needs to be called when the player should do a move.
     */
    @Override
    public void notifyForMove() {
        Thread t = new Thread(() -> {
            Position pos = calcBestPos();
            _game.place(pos, this);
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

    private Position calcBestPos() {
        ArrayList<Position> possiblePositions = _game.getBoard().getPossiblePositions(_type);
        Position bestPos = null;
        double bestPosScore = -1;

        for(Position pos : possiblePositions) {
            double score = calcScore(pos);
            if(score > bestPosScore) {
                bestPos = pos;
                bestPosScore = score;
            }
        }
        System.err.println("Winning chance of move: " + bestPosScore);
        return bestPos;
    }

    private double calcScore(Position pos) {
        int count = 0;
        for (int i = 0; i < NUM_RUNS; i++) {
            if(isRandomWin(pos)) {
                count++;
            }
        }

        return count/(double)NUM_RUNS;
    }

    private boolean isRandomWin(Position pos) {
        ReversiBoard board = _game.getBoard().copy();
        board.place(pos, _type);
        PlayerType opponentType = (_type == PlayerType.BLACK)? PlayerType.WHITE : PlayerType.BLACK;
        while(!board.isGameEnded()) {
            randomMove(board, opponentType);
            randomMove(board, _type);
        }
        Score s = board.getScore();
        return s.getNum(_type) > s.getNum(opponentType);
    }

    private void randomMove(ReversiBoard board, PlayerType type) {
        ArrayList<Position> possiblePositions = board.getPossiblePositions(type);
        if(possiblePositions.isEmpty())
            return;

        Position pos = possiblePositions.get(_random.nextInt(possiblePositions.size()));
        board.place(pos, type);
    }
}
