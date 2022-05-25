package models;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    int id;
    Date date;
    Time time;
    Person doctor;
    Person patient;

    public Appointment(int id, Date date, Time time, Person doctor, Person patient) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getInformation(){
        if(date == null || time == null){
            return "";
        }
        if(doctor != null && patient != null){
            return date + " " + time + " " + patient.getFullName() + " s " + doctor.getFullName();
        } else if(doctor != null){
            return date + " " + time + " " + doctor.getFullName();
        } else if(patient != null){
            return date + " " + time + " " + patient.getFullName();
        } else {
            return date + " " + time + " ";
        }
    }



}
