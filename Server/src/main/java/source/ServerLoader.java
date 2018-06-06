package source;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ServerLoader {

    private static ServerSocket server;
    private static String adminName = "admin";
    private static String adminPass = "admin";
    private static File root = new File("Archives");
    public static Map<Socket, ClientHandler> handlers = new HashMap<>();
    private static ServerHandler handler;

    private static final Logger log = Logger.getLogger(ServerLoader.class);

    /**
     * Запуск сервера
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Server is running");
        start();
        handle();
        end();
    }

    /**
     * Подготовка к работе сервера
     */
    private static void start() {
        log.info("Запуск сервера");
        root.mkdir();

        File idFile = new File("idFile");
        if (!idFile.exists()) {
            try {
                idFile.createNewFile();
                PrintWriter out = new PrintWriter(idFile.getAbsoluteFile());
                out.print(0);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File typeParserFile = new File("typeParserFile");
        if (!typeParserFile.exists()) {
            try {
                typeParserFile.createNewFile();
                PrintWriter out = new PrintWriter(typeParserFile.getAbsoluteFile());
                out.print(2);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
     * Старт нового потока ServerHandler
     */
    private static void handle() {
        handler = new ServerHandler(server);
        handler.start();
        read();
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

    public static ServerHandler getServerHandler() {
        return handler;
    }

    public static ClientHandler getHandle(Socket socket) {
        return handlers.get(socket);
    }

    public static void invalidate(Socket socket) {
        handlers.remove(socket);
    }
}
