package com.brechtvandevoort.reversi.gui;

import com.brechtvandevoort.reversi.model.FieldState;
import com.brechtvandevoort.reversi.model.Position;
import com.brechtvandevoort.reversi.model.ReversiGame;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by brecht on 05/04/2016.
 */
public class Field extends StackPane {

    public static final int FIELD_SIZE = 80;
    public static final int STONE_RADIUS = FIELD_SIZE / 2 - 7;

    private FieldState _fieldState;
    private boolean _highlighted;
    private ReversiGame _game;
    private Position _pos;

    public Field(ReversiGame game, Position pos) {
        super();
        _game = game;
        _pos = pos;
        update(FieldState.EMPTY, false);
        setOnMouseClicked(event -> {
            _game.place(pos);
        });
    }

    public void update(FieldState state, boolean highlighted) {
        _fieldState = state;
        _highlighted = highlighted;
        redraw();
    }

    public void redraw() {
        getChildren().clear();
        Rectangle r = new Rectangle(0,0,FIELD_SIZE,FIELD_SIZE);
        r.getStyleClass().add("field");
        if(_highlighted) {
            r.getStyleClass().add("highlighted");
        }
        getChildren().add(r);
        if(_fieldState != FieldState.EMPTY) {
            Circle c = new Circle(FIELD_SIZE/2, FIELD_SIZE/2, STONE_RADIUS);
            getChildren().add(c);
            String styleClass = (_fieldState == FieldState.BLACK)? "blackCell" : "whiteCell";
            c.getStyleClass().add(styleClass);
        }
    }
}
