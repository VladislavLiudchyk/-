package source.packet;


import source.DataBase;
import source.ServerLoader;
import source.classes.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Сообщение о входе пользователя в систему
 */
public class EnterPack extends AbstractPack{

    private User user;
    DataBase db = new DataBase();
    private static String answer;
    private static int priority;

    public EnterPack() {

    }

    public EnterPack(User user) {
        this.user = user;
    }

    @Override
    public short getId() {
        return 2;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(answer);
        oos.writeInt(priority);
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        try {
            user = (User) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle() {

        if (user.getNickname().equals(ServerLoader.getAdminName()) && user.getPassword().equals(ServerLoader.getAdminPass())) {
            answer = "Admin";
            priority = 2;
        } else {
            db.connectToDataBase();
            if (db.enter(user.getNickname(), user.getPassword())) {
                answer = "Success";
                priority = db.getPriority(user.getNickname());
            } else {
                answer = "Incorrect login or password";
                priority = -1;
            }
            db.closeDataBase();
        }
    }
}
