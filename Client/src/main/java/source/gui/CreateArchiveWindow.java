package source.gui;

import source.classes.Archive;
import source.packet.ChangeArchivePack;
import source.packet.CreateArchivePack;
import source.packet.MainTablePack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Окно для создания или редактирования архива
 */

public class CreateArchiveWindow {
    /**
     * При создании нового архива
     */
    public CreateArchiveWindow() {
        final JFrame frame = new JFrame("Case");
        frame.setSize(250, 300);
        frame.setLocation(500,250);
        frame.setResizable(false);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        frame.setLayout(grid);

        JLabel surnameLbl = new JLabel("Surname:");
        JLabel nameLbl = new JLabel("Name:");
        JLabel patronymicLbl = new JLabel("Patronymic:");
        JLabel phoneLbl = new JLabel("Phone:");
        JLabel jobLbl = new JLabel("Job:");
        final JLabel errorLbl = new JLabel("");

        Dimension dimensionText = new Dimension(100,25);
        final JTextField surname = new JTextField();
        surname.setPreferredSize(dimensionText);
        final JTextField name = new JTextField();
        name.setPreferredSize(dimensionText);
        final JTextField patronymic = new JTextField();
        patronymic.setPreferredSize(dimensionText);
        final JTextField phone = new JTextField();
        phone.setPreferredSize(dimensionText);
        final JTextField job = new JTextField();
        job.setPreferredSize(dimensionText);

        Dimension dimensionBtn = new Dimension(100,25);
        JButton saveBtn = new JButton("Save");
        saveBtn.setPreferredSize(dimensionBtn);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(surname.getText().equals("") || name.getText().equals("")
                        || patronymic.getText().equals("") || phone.getText().equals("") || job.getText().equals(""))) {
                    Main.sendPacket(new CreateArchivePack(new Archive(surname.getText(), name.getText(), patronymic.getText(),
                            phone.getText(), job.getText())));
                    frame.dispose();
                    Main.sendPacket(new MainTablePack());
                } else {
                    errorLbl.setText("Empty field");
                }
            }
        });
        if (Main.getMainWindow().getPriority() < 1) {
            saveBtn.setVisible(false);
        }

        cons.weightx = 1.0;

        cons.insets = new Insets(10, 0, 0, 5);

        cons.gridx = 0;
        cons.gridy = 0;
        grid.setConstraints(surnameLbl, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        grid.setConstraints(nameLbl, cons);

        cons.gridx = 0;
        cons.gridy = 2;
        grid.setConstraints(patronymicLbl, cons);

        cons.gridx = 0;
        cons.gridy = 3;
        grid.setConstraints(phoneLbl, cons);

        cons.gridx = 0;
        cons.gridy = 4;
        grid.setConstraints(jobLbl, cons);

        cons.gridx = 1;
        cons.gridy = 0;
        grid.setConstraints(surname, cons);

        cons.gridx = 1;
        cons.gridy = 1;
        grid.setConstraints(name, cons);

        cons.gridx = 1;
        cons.gridy = 2;
        grid.setConstraints(patronymic, cons);

        cons.gridx = 1;
        cons.gridy = 3;
        grid.setConstraints(phone, cons);

        cons.gridx = 1;
        cons.gridy = 4;
        grid.setConstraints(job, cons);

        cons.gridx = 1;
        cons.gridy = 5;
        grid.setConstraints(saveBtn, cons);


        cons.gridwidth = GridBagConstraints.CENTER;
        cons.gridx = 0;
        cons.gridy = 6;
        grid.setConstraints(errorLbl, cons);

        frame.add(surnameLbl);
        frame.add(nameLbl);
        frame.add(patronymicLbl);
        frame.add(phoneLbl);
        frame.add(jobLbl);
        frame.add(surname);
        frame.add(name);
        frame.add(patronymic);
        frame.add(phone);
        frame.add(job);
        frame.add(saveBtn);
        frame.add(errorLbl);

        frame.setVisible(true);
    }

    /**
     * При изменении или просмотре архива
     */
    public CreateArchiveWindow(Archive openArchive){
        final JFrame frame = new JFrame("Case");
        frame.setSize(250, 300);
        frame.setLocation(500,250);
        frame.setResizable(false);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        frame.setLayout(grid);

        JLabel surnameLbl = new JLabel("Surname:");
        JLabel nameLbl = new JLabel("Name:");
        JLabel patronymicLbl = new JLabel("Patronymic:");
        JLabel phoneLbl = new JLabel("Phone:");
        JLabel jobLbl = new JLabel("Job:");
        final JLabel errorLbl = new JLabel("");

        Dimension dimensionText = new Dimension(100,25);
        final JTextField surname = new JTextField(openArchive.getSurname());
        surname.setPreferredSize(dimensionText);
        final JTextField name = new JTextField(openArchive.getName());
        name.setPreferredSize(dimensionText);
        final JTextField patronymic = new JTextField(openArchive.getPatronymic());
        patronymic.setPreferredSize(dimensionText);
        final JTextField phone = new JTextField(openArchive.getPhone());
        phone.setPreferredSize(dimensionText);
        final JTextField job = new JTextField(openArchive.getJob());
        job.setPreferredSize(dimensionText);

        Dimension dimensionBtn = new Dimension(100,25);
        JButton saveBtn = new JButton("Save");
        saveBtn.setPreferredSize(dimensionBtn);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(surname.getText().equals("") || name.getText().equals("")
                        || patronymic.getText().equals("") || phone.getText().equals("") || job.getText().equals(""))) {
                    JTable table = Main.getMainWindow().getTable();
                    int index = table.getSelectionModel().getLeadSelectionIndex();
                    String id = (String) table.getModel().getValueAt(index, 0);
                    Main.sendPacket(new ChangeArchivePack(Integer.parseInt(id), new Archive(surname.getText(), name.getText(), patronymic.getText(), phone.getText(), job.getText())));
                    frame.dispose();
                    Main.sendPacket(new MainTablePack());
                } else {
                    errorLbl.setText("Empty field");
                }
            }
        });
        if (Main.getMainWindow().getPriority() < 1) {
            saveBtn.setVisible(false);
        }

        cons.weightx = 1.0;

        cons.insets = new Insets(10, 0, 0, 5);

        cons.gridx = 0;
        cons.gridy = 0;
        grid.setConstraints(surnameLbl, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        grid.setConstraints(nameLbl, cons);

        cons.gridx = 0;
        cons.gridy = 2;
        grid.setConstraints(patronymicLbl, cons);

        cons.gridx = 0;
        cons.gridy = 3;
        grid.setConstraints(phoneLbl, cons);

        cons.gridx = 0;
        cons.gridy = 4;
        grid.setConstraints(jobLbl, cons);

        cons.gridx = 1;
        cons.gridy = 0;
        grid.setConstraints(surname, cons);

        cons.gridx = 1;
        cons.gridy = 1;
        grid.setConstraints(name, cons);

        cons.gridx = 1;
        cons.gridy = 2;
        grid.setConstraints(patronymic, cons);

        cons.gridx = 1;
        cons.gridy = 3;
        grid.setConstraints(phone, cons);

        cons.gridx = 1;
        cons.gridy = 4;
        grid.setConstraints(job, cons);

        cons.gridx = 1;
        cons.gridy = 5;
        grid.setConstraints(saveBtn, cons);


        cons.gridwidth = GridBagConstraints.CENTER;
        cons.gridx = 0;
        cons.gridy = 6;
        grid.setConstraints(errorLbl, cons);

        frame.add(surnameLbl);
        frame.add(nameLbl);
        frame.add(patronymicLbl);
        frame.add(phoneLbl);
        frame.add(jobLbl);
        frame.add(surname);
        frame.add(name);
        frame.add(patronymic);
        frame.add(phone);
        frame.add(job);
        frame.add(saveBtn);
        frame.add(errorLbl);

        frame.setVisible(true);
    }
}
