package tetris;

import tetris.Model.Field;
import tetris.Model.Game;
import tetris.View.Frame;

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
