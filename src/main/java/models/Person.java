package models;

import java.sql.Date;

public class Person {

    private String address;
    private Date birthday;
    private String city;
    private int id;
    private String information;
    private boolean isDoctor;
    private String login;
    private String name;
    private String password;
    private String surname;

    public Person(){}


    public boolean isDoctor() {
        return isDoctor;
    }

    public int getId(){
        return this.id;
    }

    public String getFullName() {
        if(name == null){
            name = "";
        }
        if(surname == null){
            surname = "";
        }
        return (name.strip() + " " + surname.strip()).strip();
    }


    public String getAddress() {
        if(address == null){
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address.strip();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        if(city == null){
            return "";
        }
        return city.strip();
    }

    public void setCity(String city) {
        this.city = city.strip();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInformation() {
        if(this.information == null){
            return "";
        }
        return information;
    }

    public void setInformation(String information) {
        this.information = information.strip();
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        if(this.name == null){
            return "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name.strip();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        if(this.surname == null){
            return "";
        }
        return surname;
    }

    public void setSurname(String surname) { this.surname = surname.strip(); }
}
