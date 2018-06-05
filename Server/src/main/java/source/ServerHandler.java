package source;

import org.apache.log4j.Logger;
import source.classes.DOMParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Класс, ожидающий подключения нового клиента и создающий для него новый обработчик
 */
public class ServerHandler extends Thread{

    private final ServerSocket server;

    private static final Logger log = Logger.getLogger(ServerHandler.class);

    ServerHandler(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = server.accept();
                log.info("Accepted " + client.toString());
                ClientHandler handle = new ClientHandler(client);
                handle.start();
                ServerLoader.handlers.put(client, handle);
            } catch (SocketException e) {
                log.error(e.toString());
                return;
            } catch (IOException e) {
                log.error(e.toString());
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
