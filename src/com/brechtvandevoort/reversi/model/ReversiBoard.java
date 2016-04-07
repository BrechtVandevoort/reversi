package com.brechtvandevoort.reversi.model;

import java.util.ArrayList;

/**
 * Created by brecht on 06/04/2016.
 */
public class ReversiBoard {
    public static final int BOARD_SIZE = 8;

    private FieldState[][] _fields;

    public ReversiBoard() {
        initBoard();
    }

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

    public FieldState getFieldState(Position pos) {
        return getFieldState(pos.getRow(), pos.getCol());
    }

    public FieldState getFieldState(int row, int col) {
        return _fields[row][col];
    }

    public void setFieldState(FieldState state, Position pos) {
        setFieldState(state, pos.getRow(), pos.getCol());
    }

    public void setFieldState(FieldState state, int row, int col) {
        _fields[row][col] = state;
    }

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

    public ArrayList<Position> getPossiblePositions(PlayerType player) {
        ArrayList<Position> possiblePositions = new ArrayList<>();

        for(Position pos : getAllPositions()) {
            if(isPossiblePosition(pos, player)) {
                possiblePositions.add(pos);
            }
        }

        return possiblePositions;
    }

    public boolean isPossiblePosition(Position pos, PlayerType player) {
        return getRotatingFields(pos, player).size() > 0;
    }

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

    public boolean canPlace(PlayerType player) {
        return !getPossiblePositions(player).isEmpty();
    }

    public ArrayList<Position> getAllPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                positions.add(new Position(row, col));
            }
        }

        return positions;
    }

    public int countStones(PlayerType player) {
        FieldState state = (player == PlayerType.BLACK)? FieldState.BLACK : FieldState.WHITE;
        int count = 0;
        for(Position pos : getAllPositions()) {
            if(getFieldState(pos) == state)
                count++;
        }

        return count;
    }

    public Score getScore() {
        return new Score(countStones(PlayerType.WHITE), countStones(PlayerType.BLACK), isGameEnded());
    }

    public boolean isGameEnded() {
        return getPossiblePositions(PlayerType.BLACK).isEmpty() &&
                getPossiblePositions(PlayerType.WHITE).isEmpty();
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
