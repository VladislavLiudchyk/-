package source;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


public class ServerLoader {

    private static ServerSocket server;
    private static String adminName = "admin";
    private static String adminPass = "admin";
    private static File root = new File("Archives");

    private static final Logger log = Logger.getLogger(ServerLoader.class);

    /**
     * Запуск сервера
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Сервер запущен");
        start();
        read();
        //end();
    }

    /**
     * Подготовка к работе сервера
     */
    private static void start() {
        log.info("Запуск сервера");
        try {
            server = new ServerSocket(8888);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Читает команды из консоли
     */
    private static void read() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.equals("/end")) {
                    end();
                } else {
                    System.out.println("unknown command");
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Завершение работы сервера
     */
    public static void end() {
        log.info("Завершение работы сервера");
        try {
            server.close();
        } catch (IOException e) {
            log.error(e.toString());
        }
        System.exit(0);
    }

    public static String getAdminName() {
        return adminName;
    }

    public static String getAdminPass() {
        return adminPass;
    }

    public static File getRoot() {
        return root;
    }
}
