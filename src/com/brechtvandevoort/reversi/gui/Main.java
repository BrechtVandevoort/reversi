package com.brechtvandevoort.reversi.gui;

import com.brechtvandevoort.reversi.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer {

    private static final int BOARD_SIZE = 8;

    private GridPane _boardGrid;
    private Field[][] _fields;

    private ReversiGame _game;

    @Override
    public void start(Stage primaryStage) throws Exception{
        _game = new ReversiGame();
        _game.addObserver(this);

        initFields();
        initBoardGrid();

        Scene scene = new Scene(_boardGrid);
        scene.getStylesheets().add(Main.class.getResource("gui.css").toExternalForm());

        primaryStage.setTitle("Reversi");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    private void initFields() {
        _fields = new Field[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                _fields[i][j] = new Field(_game, new Position(i,j));
            }
        }

        updateFields();
    }

    private void updateFields() {
        ReversiBoard board = _game.getBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Position pos = new Position(i,j);
                _fields[i][j].update(board.getFieldState(pos), board.getPossiblePositions(_game.getActivePlayer()).contains(pos));
            }
        }
    }

    private void initBoardGrid() {
        _boardGrid = new GridPane();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                _boardGrid.add(_fields[i][j], i, j);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        updateFields();
    }
}
