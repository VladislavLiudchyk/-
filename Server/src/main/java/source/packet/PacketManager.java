package source.packet;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, хранящий классы сообщений и соответствующие им id
 */
public class PacketManager {

    private final static Map<Short, Class<? extends AbstractPack>> packets = new HashMap();

    static  {
        packets.put((short) 1, RegisterPack.class);
        packets.put((short) 2, EnterPack.class);
        packets.put((short) 3, AdminTablePack.class);
        packets.put((short) 4, ChangePriorityPack.class);
        packets.put((short) 5, MainTablePack.class);
        packets.put((short) 6, CreateArchivePack.class);
        packets.put((short) 7, OpenArchivePack.class);
        packets.put((short) 8, ChangeArchivePack.class);
        packets.put((short) 9, DeleteArchivePack.class);
        packets.put((short) 10, ChangeParserPack.class);
        //..........
        //..........
    }

    /**
     * Получает класс сообщения по id
     * @param id айди класса сообщения
     * @return класс сообщения
     */
    public static AbstractPack getPacket(short id) {
        try {
            return packets.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
