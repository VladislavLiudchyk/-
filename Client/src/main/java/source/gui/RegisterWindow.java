package source.gui;

import source.classes.User;
import source.packet.RegisterPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Окно для регистрации в системе нового пользователя
 */
public class RegisterWindow {

    private JFrame frame;

    private static String answer;

    public RegisterWindow() {
        frame = new JFrame("Register");
        frame.setSize(250, 250);
        frame.setLocation(500,250);
        frame.setResizable(false);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        frame.setLayout(grid);

        JLabel usernameLbl = new JLabel("Username:");
        JLabel passwordLbl = new JLabel("Password:");
        JLabel confirmLbl = new JLabel("Confirm:");
        final JLabel errorLbl = new JLabel("");

        Dimension dimensionText = new Dimension(100,25);
        final JTextField username = new JTextField();
        username.setPreferredSize(dimensionText);
        final JPasswordField password = new JPasswordField();
        password.setPreferredSize(dimensionText);
        final JPasswordField confirm = new JPasswordField();
        confirm.setPreferredSize(dimensionText);

        Dimension dimensionBtn = new Dimension(100,25);
        final JButton regBtn = new JButton("Register");
        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(username.getText().equals("") || password.getText().equals("") || confirm.getText().equals(""))) {
                    if (password.getText().equals(confirm.getText())) {
                        Main.sendPacket(new RegisterPack(new User(username.getText(), password.getText())));
                        if (answer.equals("Success")) {
                            close();
                        } else {
                            errorLbl.setText(answer);
                        }
                    } else {
                        errorLbl.setText("Password not confirmed");
                    }
                } else {
                    errorLbl.setText("Empty field");
                }
            }
        });
        regBtn.setPreferredSize(dimensionBtn);

        cons.weightx = 1.0;

        cons.insets = new Insets(10, 0, 0, 5);

        cons.gridx = 0;
        cons.gridy = 0;
        grid.setConstraints(usernameLbl, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        grid.setConstraints(passwordLbl, cons);

        cons.gridx = 0;
        cons.gridy = 2;
        grid.setConstraints(confirmLbl, cons);

        cons.gridx = 1;
        cons.gridy = 0;
        grid.setConstraints(username, cons);

        cons.gridx = 1;
        cons.gridy = 1;
        grid.setConstraints(password, cons);

        cons.gridx = 1;
        cons.gridy = 2;
        grid.setConstraints(confirm, cons);

        cons.gridx = 1;
        cons.gridy = 3;
        grid.setConstraints(regBtn, cons);


        cons.gridwidth = GridBagConstraints.CENTER;
        cons.gridx = 0;
        cons.gridy = 4;
        grid.setConstraints(errorLbl, cons);

        frame.add(usernameLbl);
        frame.add(passwordLbl);
        frame.add(confirmLbl);
        frame.add(username);
        frame.add(password);
        frame.add(confirm);
        frame.add(regBtn);
        frame.add(errorLbl);

        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }

    public static String getAnswer() {
        return answer;
    }

    public static void setAnswer(String answer) {
        RegisterWindow.answer = answer;
    }
}
