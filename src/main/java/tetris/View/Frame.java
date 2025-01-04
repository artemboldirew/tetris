package tetris.View;


import tetris.Connector;
import tetris.Model.Field;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame{
    private JButton startButton;
    private JButton stopButton;
    private JTable table;
    public Connector con;

    public void FrameMain() {
        //super("Fullscreen Window");
        // Устанавливаем поведение при закрытии окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создаем панель для размещения кнопок
        JPanel topPanel = new JPanel();
        startButton = new JButton("Начать");
        stopButton = new JButton("Остановить");
        topPanel.add(startButton);
        topPanel.add(stopButton);

        // Создаем таблицу
        Object[][] data = new Object[20][10];
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"","","","","","","","","",""});
        table = new JTable(model);
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
                if (con.field.table[row][column] == 1) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(UIManager.getColor("Table.background"));
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
        add(topPanel, BorderLayout.NORTH); // Добавляем панель с кнопками в верхнюю часть
        add(tableScrollPane, BorderLayout.CENTER);
        //table.repaint();// Добавляем таблицу в центр
        setVisible(true);

    }

    public void review() {
        table.repaint();
    }

    public void buttonPushed() {
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
    }

}
