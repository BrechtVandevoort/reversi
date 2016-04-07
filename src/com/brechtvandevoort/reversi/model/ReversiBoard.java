package com.brechtvandevoort.reversi.model;

import java.util.ArrayList;

/**
 * Implementation of the ReversiBoard.
 *
 * Created by brecht on 06/04/2016.
 */
public class ReversiBoard {
    public static final int BOARD_SIZE = 8;

    private FieldState[][] _fields;

    /**
     * Constructor.
     */
    public ReversiBoard() {
        initBoard();
    }

    /**
     * Initialises the board.
     */
    public void initBoard() {
        _fields = new FieldState[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                _fields[row][col] = FieldState.EMPTY;
            }
        }

        setFieldState(FieldState.BLACK, new Position(3,3));
        setFieldState(FieldState.BLACK, new Position(4,4));
        setFieldState(FieldState.WHITE, new Position(3,4));
        setFieldState(FieldState.WHITE, new Position(4,3));
    }

    /**
     * Returns the FieldState on the given position.
     * @param pos The Position.
     * @return The FieldState.
     */
    public FieldState getFieldState(Position pos) {
        return getFieldState(pos.getRow(), pos.getCol());
    }

    /**
     * Returns the FieldState on the given position.
     * @param row The row of the position.
     * @param col The col of the position.
     * @return The FieldState.
     */
    public FieldState getFieldState(int row, int col) {
        return _fields[row][col];
    }

    /**
     * Sets the FieldState on the given Position.
     * @param state The FieldState to set.
     * @param pos The Position.
     */
    public void setFieldState(FieldState state, Position pos) {
        setFieldState(state, pos.getRow(), pos.getCol());
    }

    /**
     * Sets the FieldState on the given position.
     * @param state The FieldState to set.
     * @param row The row of the position.
     * @param col The col of the position.
     */
    public void setFieldState(FieldState state, int row, int col) {
        _fields[row][col] = state;
    }

    /**
     * Places a stone on the board.
     * @param pos The Position to place the stone on.
     * @param player The playerType of the player placing the stone.
     * @return True if accepted, false if not.
     */
    public boolean place(Position pos, PlayerType player) {
        if(!getPossiblePositions(player).contains(pos))
            return false;

        FieldState state = (player == PlayerType.BLACK)? FieldState.BLACK : FieldState.WHITE;
        for(Position p : getRotatingFields(pos, player)) {
            setFieldState(state, p);
        }
        setFieldState(state, pos);

        return true;
    }

    /**
     * Returns a list of all the possible Positions for the given player.
     * @param player The PlayerType.
     * @return A list containing all the possible Positions.
     */
    public ArrayList<Position> getPossiblePositions(PlayerType player) {
        ArrayList<Position> possiblePositions = new ArrayList<>();

        for(Position pos : getAllPositions()) {
            if(isPossiblePosition(pos, player)) {
                possiblePositions.add(pos);
            }
        }

        return possiblePositions;
    }

    /**
     * Checks if a given Position is possible for a given player.
     * @param pos The Position to check.
     * @param player The PlayerType.
     * @return True if the Position is possible, false if not.
     */
    public boolean isPossiblePosition(Position pos, PlayerType player) {
        return getRotatingFields(pos, player).size() > 0;
    }

    /**
     * Returns a list of all the stones that would rotate when a stone is placed on a given Position.
     * @param pos The Position to place the stone.
     * @param player The PlayerType of the player placing the stone.
     * @return The list containing all the Positions of the rotating stones.
     */
    public ArrayList<Position> getRotatingFields(Position pos, PlayerType player) {
        ArrayList<Position> positions = new ArrayList<>();

        if(getFieldState(pos) != FieldState.EMPTY) {
            return positions;
        }

        FieldState ownField = (player == PlayerType.BLACK)? FieldState.BLACK: FieldState.WHITE;
        FieldState opponentField = (player == PlayerType.BLACK)? FieldState.WHITE : FieldState.BLACK;

        positions.addAll(processRotatingFieldsDirection(pos, ownField, opponentField, -1, 0));
        positions.addAll(processRotatingFieldsDirection(pos, ownField, opponentField, 1, 0));
        positions.addAll(processRotatingFieldsDirection(pos, ownField, opponentField, 0, -1));
        positions.addAll(processRotatingFieldsDirection(pos, ownField, opponentField, 0, 1));
        positions.addAll(processRotatingFieldsDirection(pos, ownField, opponentField, -1, -1));
        positions.addAll(processRotatingFieldsDirection(pos, ownField, opponentField, 1, -1));
        positions.addAll(processRotatingFieldsDirection(pos, ownField, opponentField, -1, 1));
        positions.addAll(processRotatingFieldsDirection(pos, ownField, opponentField, 1, 1));

        return positions;
    }

    /**
     * Checks if a player can do a move.
     * @param player The PlayerType.
     * @return True if the player can do a move, false if not.
     */
    public boolean canPlace(PlayerType player) {
        return !getPossiblePositions(player).isEmpty();
    }

    /**
     * Returns a list of all the positions on the board.
     * @return A list containing all the valid Positions on the board.
     */
    public ArrayList<Position> getAllPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                positions.add(new Position(row, col));
            }
        }

        return positions;
    }

    /**
     * Counts the number of stones a player has on the board.
     * @param player The playerType.
     * @return The number of stones the player currently has on the board.
     */
    public int countStones(PlayerType player) {
        FieldState state = (player == PlayerType.BLACK)? FieldState.BLACK : FieldState.WHITE;
        int count = 0;
        for(Position pos : getAllPositions()) {
            if(getFieldState(pos) == state)
                count++;
        }

        return count;
    }

    /**
     * Returns the current Score.
     * @return The current Score.
     */
    public Score getScore() {
        return new Score(countStones(PlayerType.WHITE), countStones(PlayerType.BLACK), isGameEnded());
    }

    /**
     * Checks if the game is over.
     * @return True if the game is ended, false if not.
     */
    public boolean isGameEnded() {
        return getPossiblePositions(PlayerType.BLACK).isEmpty() &&
                getPossiblePositions(PlayerType.WHITE).isEmpty();
    }

    /**
     * Creates a copy of the ReversiBoard.
     * @return The copy of this ReversiBoard.
     */
    public ReversiBoard copy() {
        ReversiBoard board = new ReversiBoard();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board.setFieldState(getFieldState(row, col), row, col);
            }
        }

        return board;
    }

    private ArrayList<Position> processRotatingFieldsDirection(Position pos, FieldState ownField, FieldState opponentField, int rowDelta, int colDelta) {
        ArrayList<Position> rotating = new ArrayList<>();

        int row = pos.getRow()+rowDelta;
        int col = pos.getCol()+colDelta;
        while(processRotatingField(new Position(row,col), ownField, opponentField, rotating)) {
            row += rowDelta;
            col += colDelta;
        }

        return rotating;
    }

    private boolean processRotatingField(Position pos, FieldState ownField, FieldState opponentField, ArrayList<Position> rotating) {
        if(!isValidPosition(pos)) {
            rotating.clear();
            return false;
        }

        FieldState state = getFieldState(pos);
        if(state == opponentField) {
            rotating.add(pos);
            return true;
        }
        else if(state == ownField) {
            return false;
        }
        else { //state == FieldState.EMPTY
            rotating.clear();
            return false;
        }
    }

    private boolean isValidPosition(Position pos) {
        return pos.getCol() >= 0 && pos.getCol() < BOARD_SIZE &&
                pos.getRow() >= 0 && pos.getRow() < BOARD_SIZE;
    }
}
