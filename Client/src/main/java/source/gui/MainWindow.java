package source.gui;

import source.packet.DeleteArchivePack;
import source.packet.MainTablePack;
import source.packet.OpenArchivePack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Главное окно клиента
 */
public class MainWindow {

    private JTable table = new JTable(3, 3);

    private int priority = 0;

    MainWindow(int priority) {
        this.priority = priority;

        final JFrame frame = new JFrame("Main");
        frame.setSize(515, 470);
        frame.addWindowListener(new MyWindowListener());
        frame.setLocation(400,100);
        frame.setResizable(false);

        frame.setLayout(new FlowLayout());

        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);

        ImageIcon refreshImg = new ImageIcon(getClass().getResource("/S015.png"));
        ImageIcon backImg = new ImageIcon(getClass().getResource("/S017.png"));
        ImageIcon caseImg = new ImageIcon(getClass().getResource("/S085.png"));
        ImageIcon delImg = new ImageIcon(getClass().getResource("/S016.png"));

        JButton refreshBtn = new JButton(new ImageIcon(refreshImg.getImage().getScaledInstance(30, 30,
                refreshImg.getImage().SCALE_DEFAULT)));
        //JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBorderPainted(false);
        refreshBtn.setVisible(true);
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.sendPacket(new MainTablePack());
            }
        });
        toolBar.add(refreshBtn);
        JButton caseBtn = new JButton(new ImageIcon(caseImg.getImage().getScaledInstance(30, 30,
                caseImg.getImage().SCALE_DEFAULT)));
        //JButton caseBtn = new JButton("Case");
        caseBtn.setBorderPainted(false);
        caseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        toolBar.add(caseBtn);
        JButton delBtn = new JButton(new ImageIcon(delImg.getImage().getScaledInstance(30, 30,
                delImg.getImage().SCALE_DEFAULT)));
        //JButton delBtn = new JButton("Del");
        delBtn.setBorderPainted(false);
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectionModel().getLeadSelectionIndex();
                String id = (String) table.getModel().getValueAt(index, 0);
                System.out.println("id: " + id);
                Main.sendPacket(new DeleteArchivePack(Integer.parseInt(id)));
                Main.sendPacket(new MainTablePack());
            }
        });
        toolBar.add(delBtn);
        if (priority < 2) {
            caseBtn.setVisible(false);
            delBtn.setVisible(false);
        }
        JButton backBtn = new JButton(new ImageIcon(backImg.getImage().getScaledInstance(30, 30,
                backImg.getImage().SCALE_DEFAULT)));
        //JButton backBtn = new JButton("Back");
        backBtn.setBorderPainted(false);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main.setEnterWindow(new EnterWindow());
            }
        });
        toolBar.add(backBtn);

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    int index = table.getSelectionModel().getLeadSelectionIndex();
                    String id = (String) table.getModel().getValueAt(index, 0);
                    System.out.println("id: " + id);
                    Main.sendPacket(new OpenArchivePack(Integer.parseInt(id)));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(toolBar);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    public JTable getTable() {
        return table;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
