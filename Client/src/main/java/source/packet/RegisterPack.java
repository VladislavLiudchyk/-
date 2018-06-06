package source.packet;

import org.omg.CORBA.Object;
import source.classes.User;
import source.gui.RegisterWindow;

import java.io.*;

/**
 * Сообщение о регистрации нового пользователя
 */
public class RegisterPack extends AbstractPack{
    private User user;
    private String answer;
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
        oos.writeObject(user);
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        answer = ois.readUTF();
    }

    @Override
    public void handle() {
        RegisterWindow.setAnswer(answer);
    }

}
