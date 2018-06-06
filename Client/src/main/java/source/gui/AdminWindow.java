package source.gui;

import source.packet.ChangeParserPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Окно для админа
 */
public class AdminWindow {

    private JTable table = new JTable(3, 3);
    private JComboBox comboBox;

    AdminWindow() {
        final JFrame frame = new JFrame("Admin");
        frame.setSize(515, 500);
        frame.addWindowListener(new MyWindowListener());
        frame.setLocation(400,100);
        frame.setResizable(false);

        frame.setLayout(new FlowLayout());

        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);

        ImageIcon backImg = new ImageIcon(getClass().getResource("/S017.png"));

        JButton backBtn = new JButton(new ImageIcon(backImg.getImage().getScaledInstance(30, 30, backImg.getImage().SCALE_DEFAULT)));
        backBtn.setBorderPainted(false);
        backBtn.setToolTipText("Назад");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main.setEnterWindow(new EnterWindow());
            }
        });
        toolBar.add(backBtn);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(toolBar);
        frame.add(scrollPane);

        frame.add(new JLabel("Type of parser:"));

        comboBox = new JComboBox();
        comboBox.addItem("DOM");
        comboBox.addItem("SAX");
        comboBox.addItem("StAX");
        comboBox.addItem("JDOM");
        comboBox.addActionListener(new ActionListener() {
            private int count = 1;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count % 2 != 0) {
                    count++;
                    return;
                }
                JComboBox cb = (JComboBox)e.getSource();
                Main.sendPacket(new ChangeParserPack(cb.getSelectedIndex()));
                count++;
            }
        });
        frame.add(comboBox);

        frame.setVisible(true);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(JComboBox comboBox) {
        this.comboBox = comboBox;
    }
}
