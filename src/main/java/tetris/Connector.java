package tetris;

import tetris.model.Field;
import tetris.model.Game;
import tetris.view.Frame;

public class Connector {
    public Game game;
    public Field field;
    public Frame frame;

    public Connector(Game game, Field field, Frame frame) {
        this.game = game;
        this.field = field;
        this.frame = frame;
    }
}
