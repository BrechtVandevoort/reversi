package com.brechtvandevoort.reversi.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int BOARD_SIZE = 8;

    private GridPane _board;
    private Field[][] _fields;

    @Override
    public void start(Stage primaryStage) throws Exception{
        initFields();
        initBoard();

        Scene scene = new Scene(_board);
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
                _fields[i][j] = new Field();
                _fields[i][j].update(Field.FieldState.FIELD_BLACK);
            }
        }
    }

    private void initBoard() {
        _board = new GridPane();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                _board.add(_fields[i][j], i, j);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
