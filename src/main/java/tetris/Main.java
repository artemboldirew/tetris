package tetris;

import tetris.Model.Field;
import tetris.Model.Game;
import tetris.View.Frame;

import javax.swing.*;
// сделай так чтобы действия не зависили от порядка точек
public class Main {
    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(() -> {
            Field field = new Field();
            Frame frame = new Frame();
            Game game = new Game();
            Connector con = new Connector(game, field, frame);
            game.con = con;
            field.con = con;
            frame.con = con;
            frame.FrameMain();
        });
    }
}
