package source.classes;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Класс для архивирования и разархивирования персональных данных
 */
public class ZIP {

    private static final Logger log = Logger.getLogger(ZIP.class);

    /**
     * Преобразует файл в zip архив
     * @param file исходный файл
     * @param name имя для zip архива
     */
    public static void toZIP(File file, String name) {

        log.info("Запись в архив");

        File ZIPFile = new File(name);

        try (ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(ZIPFile));
             FileInputStream fis = new FileInputStream(file))
        {
            ZipEntry entry1 = new ZipEntry(file.getPath());
            zOut.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // добавляем содержимое к архиву
            zOut.write(buffer);
            // закрываем текущую запись для новой записи
            zOut.closeEntry();
        }
        catch(Exception ex){
            log.error(ex.toString());
        }
    }

    /**
     * Разархивирование zip-архива
     * @param ZIPFile zip архив
     * @return файл из архива
     */
    public static File fromZIP(File ZIPFile) {

        log.info("Чтение из архива");

        File file = new File("resultFromZip");

        try {
            ZipFile zip = new ZipFile(ZIPFile.getPath());
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println(entry.getName());

                write(zip.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
            }

            zip.close();
        } catch (IOException e) {
            log.error(e.toString());
        }

        return file;
    }

    /**
     * Записывает данные из потока in в поток out
     * @param in поток для чтения данных
     * @param out поток для записи данных
     * @throws IOException
     */
    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}

