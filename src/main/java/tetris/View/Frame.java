package tetris.View;

import tetris.Connector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Frame extends JFrame {
    private JButton startButton;
    private JButton stopButton;
    private JTextField scoreField;
    private JTable table;
    public Connector con;

    public void FrameMain() {
        //super("Fullscreen Window");
        // Устанавливаем поведение при закрытии окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создаем панель для размещения кнопок
        JPanel topPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel emptyPanel = new JPanel();
        startButton = new JButton("Начать");
        String scoreString = "Ваш счёт: " + con.game.score;
        scoreField = new JTextField(scoreString);
        scoreField.setEditable(false);
        scoreField.setFocusable(false);
        stopButton = new JButton("Остановить");
        startButton.setFocusable(false);
        stopButton.setFocusable(false);
        setFocusable(false);
        topPanel.add(scoreField);
        topPanel.add(startButton);
        topPanel.add(stopButton);

        // Создаем таблицу
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

                // Проверяем значение в ячейке
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

        // Создаем скролл для таблицы
        JScrollPane tableScrollPane = new JScrollPane(table);
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.add(Box.createVerticalGlue());
        wrapperPanel.add(table);
        wrapperPanel.add(Box.createVerticalGlue());
        tableScrollPane.setViewportView(wrapperPanel);

        // Устанавливаем наш layout
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLocationRelativeTo(null);
        add(emptyPanel, BorderLayout.SOUTH);
        //add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH); // Добавляем панель с кнопками в верхнюю часть
        add(tableScrollPane, BorderLayout.CENTER);
        //table.repaint();// Добавляем таблицу в центр
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Thread(() -> {
                    try {
                        con.game.isRunning = true;
                        con.game.mainGame(); // Запускаем основной игровой цикл
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }).start(); // Запускаем новый поток
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    con.game.isRunning = false;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        addKeyBindings();
        setVisible(true);
    }


    public void review() {
        table.repaint();
    }

    private void addKeyBindings() {
        // Получаем InputMap и ActionMap у корневого слоя (root pane)
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        // Привязываем действие к клавише "Вправо"
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "rapidFall");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "rotate");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con.field.moveRight();
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con.field.moveLeft();
            }
        });
        actionMap.put("rapidFall", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con.field.rapidFall();
            }
        });
        actionMap.put("rotate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con.field.mainRotation();
            }
        });
    }
    public void setScore(int score) {
        String scoreString = "Ваш счёт: " + con.game.score;
        scoreField.setText(scoreString);
    }

}
