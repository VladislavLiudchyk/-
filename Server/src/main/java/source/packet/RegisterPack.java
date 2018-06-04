package source.packet;

import source.DataBase;
import source.ServerLoader;
import source.classes.User;
import java.io.*;

/**
 * Сообщение о регистрации нового пользователя
 */
public class RegisterPack extends AbstractPack{

    private User user;

    DataBase db = new DataBase();

    private static String answer;

    public RegisterPack() {

    }

    public RegisterPack(User user) {
        this.user = user;
    }

    @Override
    public short getId() {
        return 1;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(answer);
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
        db.connectToDataBase();
        if (!db.isRegistered(user.getNickname())) {
            db.insertIntoUsers(user.getNickname(), user.getPassword(), user.getPriority());
            System.out.println("Success");
            answer = "Success";
        } else {
            System.out.println("User is registered");
            answer = "User is registered";
        }
        db.closeDataBase();
    }
}
