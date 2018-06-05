package source.gui;

import org.apache.log4j.Logger;
import source.classes.User;
import source.packet.AbstractPack;
import source.packet.PacketManager;

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

    private static ConnectionWindow connectionWindow;
    private static EnterWindow enterWindow;
    private static RegisterWindow registerWindow;

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
                connectionWindow = new ConnectionWindow();
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
                AbstractPack packet = PacketManager.getPacket(id);
                packet.read(ois);
                packet.handle();
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
    private static void connect(String IP, Integer Port) {
        log.info("Подключение");
        try {
            //socket = new Socket("localhost", 8888);
            socket = new Socket(IP, Port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    /**
     * Отправляет пакет серверу и ожидает ответ
     * @param packet
     */
    public synchronized static void sendPacket(AbstractPack packet) {
        try {
            oos.writeShort(packet.getId());
            packet.write(oos);
            oos.flush();
            log.info("Отправлен пакет" + packet.getId());
            waitServer();
        } catch (Exception e) {
            log.error(e.toString());
            JOptionPane.showMessageDialog(null, "Сервер недоступен");
        }
    }

    public static ConnectionWindow getConnectionWindow() {
        return connectionWindow;
    }

    public static void setConnectionWindow() {
        Main.connectionWindow = connectionWindow;
    }

    public static void setConnection(String IP, Integer Port) {
        connect(IP, Port);
    }

    public static RegisterWindow getRegisterWindow() {
        return registerWindow;
    }

    public static void setRegisterWindow(RegisterWindow registerWindow) {
        Main.registerWindow = registerWindow;
    }

    public static EnterWindow getEnterWindow() {
        return enterWindow;
    }

    public static void setEnterWindow(EnterWindow enterWindow) { Main.enterWindow = enterWindow; }

}
