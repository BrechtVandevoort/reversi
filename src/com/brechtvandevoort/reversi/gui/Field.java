package com.brechtvandevoort.reversi.gui;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by brecht on 05/04/2016.
 */
public class Field extends StackPane {

    public static final int FIELD_SIZE = 80;
    public static final int STONE_RADIUS = FIELD_SIZE / 2 - 7;

    public enum FieldState {FIELD_EMPTY, FIELD_BLACK, FIELD_WHITE};

    private FieldState _fieldState;

    public Field() {
        super();
        update(FieldState.FIELD_EMPTY);
    }

    public void update(FieldState state) {
        _fieldState = state;
        redraw();
    }

    public void redraw() {
        getChildren().clear();
        Rectangle r = new Rectangle(0,0,FIELD_SIZE,FIELD_SIZE);
        r.getStyleClass().add("field");
        getChildren().add(r);
        if(_fieldState != FieldState.FIELD_EMPTY) {
            Circle c = new Circle(FIELD_SIZE/2, FIELD_SIZE/2, STONE_RADIUS);
            getChildren().add(c);
            String styleClass = (_fieldState == FieldState.FIELD_BLACK)? "blackCell" : "whiteCell";
            c.getStyleClass().add(styleClass);
        }
    }
}
