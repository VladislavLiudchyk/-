package source;

import org.apache.log4j.Logger;
import org.omg.CORBA.Object;
import source.classes.DOMParser;
import source.classes.User;
import source.packet.AbstractPack;
import source.packet.PacketManager;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Класс, представляющий собой поток для обработки сообщений одного клиента
 */
public class ClientHandler extends Thread{
    private final Socket client;
    private User user = new User("Noname", "Noname");
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private static final Logger log = Logger.getLogger(ClientHandler.class);

    ClientHandler(Socket client) throws IOException {
        this.client = client;
        ois = new ObjectInputStream(client.getInputStream());
        oos = new ObjectOutputStream(client.getOutputStream());
    }

    @Override
    public synchronized void run() {

        log.info("Запуск нового потока - обработчика клиента");

        while (true) {
            try {
                if (ois.available() > 0) {
                    short id = ois.readShort();
                    AbstractPack packet = PacketManager.getPacket(id);
                    packet.read(ois);
                    packet.handle();
                    sendPacket(packet);
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SocketException | EOFException e) {
                log.error(e.toString());
                ServerLoader.handlers.remove(client);
                try {
                    oos.close();
                    ois.close();
                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                log.info(user.getNickname() + " interrupted");
                currentThread().interrupt();
                return;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Отправляет сообщение клиенту
     * @param packet сообщение
     */
    public synchronized void sendPacket(AbstractPack packet) {
        try {
            oos.writeShort(packet.getId());
            packet.write(oos);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void invalidate() {
        ServerLoader.invalidate(client);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
