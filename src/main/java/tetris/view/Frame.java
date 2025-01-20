package tetris.view;

import tetris.Connector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Frame extends JFrame {
    private JButton startButton;
    private JTextField scoreField;
    private JTable table;
    public Connector con;

    public void FrameMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel emptyPanel = new JPanel();
        startButton = new JButton("Начать");
        String scoreString = "Ваш счёт: " + con.game.score;
        scoreField = new JTextField(scoreString);
        Dimension scoreRect = new Dimension(200, 25);
        scoreField.setMinimumSize(scoreRect);
        scoreField.setPreferredSize(scoreRect);
        scoreField.setEditable(false);
        scoreField.setFocusable(false);
        startButton.setFocusable(false);
        setFocusable(false);
        topPanel.add(scoreField);
        topPanel.add(startButton);

        Object[][] data = new Object[20][10];
        DefaultTableModel model = new DefaultTableModel(data, new String[] {"", "", "", "", "", "", "", "", "", ""});
        table = new JTable(model);
        table.setFocusable(false);
        table.setRowHeight(20);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 10; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(20);
            table.getColumnModel().getColumn(i).setMaxWidth(20);
        }

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (con.field.table[row][column] == 0) {
                    c.setBackground(Color.WHITE);
                }
                else if (con.field.table[row][column] == 1) {
                    c.setBackground(Color.RED);
                }
                else if (con.field.table[row][column] == 2) {
                    c.setBackground(Color.BLUE);
                }
                else if (con.field.table[row][column] == 3) {
                    c.setBackground(Color.GREEN);
                }
                else if (con.field.table[row][column] == 4) {
                    c.setBackground(Color.ORANGE);
                }
                else if (con.field.table[row][column] == 5) {
                    c.setBackground(Color.BLACK);
                }
                else if (con.field.table[row][column] == -1) {
                    c.setBackground(Color.GRAY);
                }
                else {
                    c.setBackground(Color.MAGENTA);
                }

                return c;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.add(Box.createVerticalGlue());
        wrapperPanel.add(table);
        wrapperPanel.add(Box.createVerticalGlue());
        tableScrollPane.setViewportView(wrapperPanel);

        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLocationRelativeTo(null);
        add(emptyPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Thread(() -> {
                    try {
                        if (!con.game.isRunning) {
                            con.game.isRunning = true;
                            con.game.mainGame();
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }).start();
            }
        });

        addKeyBindings();
        setVisible(true);
    }


    public void review() {
        table.repaint();
    }

    private void addKeyBindings() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "rapidFall");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "rotate");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "start");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (con.game.isRunning) {
                    con.field.moveRight();
                    review();
                }
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (con.game.isRunning) {
                    con.field.moveLeft();
                    review();
                }
            }
        });
        actionMap.put("rapidFall", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (con.game.isRunning) {
                    con.field.rapidFall();
                    review();
                }
            }
        });
        actionMap.put("rotate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (con.game.isRunning) {
                    con.field.rotation();
                    review();
                }
            }
        });
        actionMap.put("start", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    try {
                        if (!con.game.isRunning) {
                            con.game.isRunning = true;
                            con.game.mainGame(); // Запускаем основной игровой цикл
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }).start();
            }
        });
    }

    public void setScore(boolean isStart) {
        String scoreString;
        if (isStart) {
            scoreString = "Ваш счёт: " + con.game.score;
        }
        else {
            scoreString = "Ваш финальный счёт: " + con.game.score;
        }
        scoreField.setText(scoreString);
    }

}
