package source.packet;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Сообщение об изменении приоритета пользователя
 */

public class ChangePriorityPack extends AbstractPack{

    private String username;
    private int priority;

    public ChangePriorityPack() {

    }

    public ChangePriorityPack(String username, int priority) {
        this.username = username;
        this.priority = priority;
    }

    @Override
    public short getId() {
        return 4;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(username);
        oos.writeInt(priority);
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {

    }

    @Override
    public void handle() {

    }
}
