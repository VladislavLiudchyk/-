package source.gui;

import org.apache.log4j.Logger;
import source.classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Главный класс юзера
 */
public class Main {

    private static Socket socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    private static final Logger log = Logger.getLogger(Main.class);

    /**
     * Поключается к серверу
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                connect();
            }
        });
    }

    /**
     * Ожидает ответ сервера и обрабатывает его
     */
    private synchronized static void waitServer() {
        log.info("Ожидание ответа сервера");
        while (true) {
            try {
                if (ois.available() <= 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                short id = ois.readShort();
                log.info("Получен пакет: " + id);
                break;
            } catch (Exception e) {
                log.error(e.toString());
                end();
            }
        }
    }

    /**
     * Отключает клиента от сервера
     */
    public static void end() {
        try {
            log.info("Отключение");
            oos.close();
            ois.close();
            socket.close();
        } catch (Exception e) {
            log.error(e.toString());
            System.exit(0);
        }
        System.exit(0);
    }

    /**
     * Подключает клиента к серверу
     */
    private static void connect() {
        log.info("Подключение");
        try {
            socket = new Socket("localhost", 8888);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
}
