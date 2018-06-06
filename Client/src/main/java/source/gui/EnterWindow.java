package source.gui;

import source.classes.User;
import source.packet.EnterPack;
import source.packet.AdminTablePack;
import source.packet.MainTablePack;
import source.packet.RegisterPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Окно для входа пользователя в систему
 */
public class EnterWindow {

    private JFrame frame;

    private static String answer;
    private static int priority;

    EnterWindow() {
        frame = new JFrame("Enter");
        frame.setSize(250, 200);
        frame.addWindowListener(new MyWindowListener());
        frame.setLocation(500,250);
        frame.setResizable(false);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        frame.setLayout(grid);

        JLabel usernameLbl = new JLabel("Username:");
        JLabel passwordLbl = new JLabel("Password:");
        final JLabel errorLbl = new JLabel("");

        Dimension dimensionText = new Dimension(100,25);
        final JTextField username = new JTextField();
        username.setPreferredSize(dimensionText);
        final JPasswordField password = new JPasswordField();
        password.setPreferredSize(dimensionText);

        Dimension dimensionBtn = new Dimension(100,25);
        JButton regBtn = new JButton("Register");
        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setRegisterWindow(new RegisterWindow());
            }
        });
        regBtn.setPreferredSize(dimensionBtn);
        JButton enterBtn = new JButton("Enter");
        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(username.getText().equals("") || password.getText().equals(""))) {
                    Main.sendPacket(new EnterPack(new User(username.getText(), password.getText())));
                    if (answer.equals("Admin")) {
                        Main.setAdminWindow(new AdminWindow());
                        Main.sendPacket(new AdminTablePack());
                        frame.dispose();
                    } else if (answer.equals("Success")) {
                        Main.setMainWindow(new MainWindow(priority));
                        Main.sendPacket(new MainTablePack());
                        frame.dispose();
                    } else {
                        errorLbl.setText(answer);
                    }
                } else {
                    errorLbl.setText("Empty field");
                }
            }
        });
        enterBtn.setPreferredSize(dimensionBtn);

        cons.weightx = 1.0;

        cons.insets = new Insets(10, 0, 0, 5);

        cons.gridx = 0;
        cons.gridy = 0;
        grid.setConstraints(usernameLbl, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        grid.setConstraints(passwordLbl, cons);

        cons.gridx = 1;
        cons.gridy = 0;
        grid.setConstraints(username, cons);

        cons.gridx = 1;
        cons.gridy = 1;
        grid.setConstraints(password, cons);

        cons.gridx = 0;
        cons.gridy = 2;
        grid.setConstraints(regBtn, cons);

        cons.gridx = 1;
        cons.gridy = 2;
        grid.setConstraints(enterBtn, cons);

        cons.gridwidth = GridBagConstraints.CENTER;
        cons.gridx = 0;
        cons.gridy = 3;
        grid.setConstraints(errorLbl, cons);

        frame.add(usernameLbl);
        frame.add(passwordLbl);
        frame.add(username);
        frame.add(password);
        frame.add(regBtn);
        frame.add(enterBtn);
        frame.add(errorLbl);

        frame.setVisible(true);
    }

    public static String getAnswer() {
        return answer;
    }

    public static void setAnswer(String answer) {
        EnterWindow.answer = answer;
    }

    public static void setPriority(int priority) {
        EnterWindow.priority = priority;
    }

    public static int getPriority() {
        return priority;
    }
}
