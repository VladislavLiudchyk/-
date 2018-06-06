package source.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Окно для установки соединения с сервером
 */
public class ConnectionWindow {

    private JFrame frame;

    private static String answer;
    private static int priority;

    private static String IP;
    private static int Port;

    ConnectionWindow() {
        frame = new JFrame("Connection");
        frame.setSize(250, 200);
        frame.addWindowListener(new MyWindowListener());
        frame.setLocation(500,250);
        frame.setResizable(false);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        frame.setLayout(grid);

        JLabel usernameLbl = new JLabel("IP-Address:");
        JLabel passwordLbl = new JLabel("Port:");
        final JLabel errorLbl = new JLabel("");

        Dimension dimensionText = new Dimension(100,25);
        final JTextField username = new JTextField();
        username.setPreferredSize(dimensionText);
        final JPasswordField password = new JPasswordField();
        password.setPreferredSize(dimensionText);

        Dimension dimensionBtn = new Dimension(100,25);
        JButton regBtn = new JButton("Exit");
        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.end();
            }
        });
        regBtn.setPreferredSize(dimensionBtn);
        JButton enterBtn = new JButton("Enter");
        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(username.getText().equals("") || password.getText().equals(""))) {
                    IP = username.getText();
                    Port = Integer.parseInt(password.getText());
                    JOptionPane.showMessageDialog(null, "IP" + IP + " | Port" + Port);
                    Main.setConnection(IP, Port);
                    Main.setEnterWindow(new EnterWindow());
                    frame.dispose();
                }
                else {
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
        ConnectionWindow.answer = answer;
    }

    public static void setPriority(int priority) { ConnectionWindow.priority = priority; }

    public static int getPriority() {
        return priority;
    }

    public static String getIP() {
        return IP;
    }

    public static void setIP(String IP) {
        ConnectionWindow.IP = IP;
    }

    public static int getPort() {
        return Port;
    }

    public static void setPort(Integer Port) {
        ConnectionWindow.Port = Port;
    }
}

