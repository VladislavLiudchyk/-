package source.classes;

import java.io.Serializable;

/**
 * Класс, объекты которого представляют собой архивы
 */
public class Archive implements Serializable {

    private String surname;
    private String name;
    private String patronymic;
    private String phone;
    private String job;

    public Archive() {

    }

    public Archive(String surname, String name, String patronymic, String phone, String job) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone = phone;
        this.job = job;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
