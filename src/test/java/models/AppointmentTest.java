package models;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void getInformationNullValues() {
        String dateString = "1989-09-26";
        String timeString = "11:15:00";

        Appointment appointment1 = new Appointment(0, null, Time.valueOf(timeString), null, null);
        assertEquals("", appointment1.getInformation());

        Appointment appointment2 = new Appointment(0, Date.valueOf(dateString), null, null, null);
        assertEquals("", appointment2.getInformation());

        Appointment appointment3 = new Appointment(0, null, null, null, null);
        assertEquals("", appointment3.getInformation());

        Appointment appointment4 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), null, null);
        assertEquals("1989-09-26 11:15:00", appointment4.getInformation());
    }

    @Test
    void getInformationEmptyPerson() {
        String dateString = "1989-09-26";
        String timeString = "11:15:00";
        Person person = new Person();

        Appointment appointment = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), null, person);
        assertEquals("1989-09-26 11:15:00", appointment.getInformation());

        Appointment appointment2 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, null);
        assertEquals("1989-09-26 11:15:00", appointment2.getInformation());

        Appointment appointment3 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, person);
        assertEquals("1989-09-26 11:15:00", appointment3.getInformation());
    }

    @Test
    void getInformationIncompleteValues() {
        String dateString = "1989-09-26";
        String timeString = "11:15:00";
        Person person = new Person();
        person.setName("John");
        person.setSurname("Smith");

        Appointment appointment = new Appointment(0, Date.valueOf(dateString), null, person, person);
        assertEquals("", appointment.getInformation());

        Appointment appointment2 = new Appointment(0, null, Time.valueOf(timeString), person, null);
        assertEquals("", appointment2.getInformation());

        Appointment appointment3 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, person);
        assertEquals("1989-09-26 11:15:00 John Smith s John Smith", appointment3.getInformation());
    }

    @Test
    void getInformationCorrectValues() {
        String dateString = "1989-09-26";
        String timeString = "11:15:00";
        Person person = new Person();
        person.setName("John");
        person.setSurname("Smith");
        Person person1 = new Person();
        person1.setSurname("Sparrow");

        Appointment appointment = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, null);
        assertEquals("1989-09-26 11:15:00 John Smith", appointment.getInformation());

        Appointment appointment2 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), null, person1);
        assertEquals("1989-09-26 11:15:00 Sparrow", appointment2.getInformation());

        Appointment appointment3 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, person1);
        assertEquals("1989-09-26 11:15:00 Sparrow s John Smith", appointment3.getInformation());
    }

    @Test
    void getInformationSpaces() {
        String dateString = "1989-09-26";
        String timeString = "11:15:00";
        Person person = new Person();
        person.setSurname("Smith");
        Person person1 = new Person();
        person1.setSurname("    ");

        Appointment appointment = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, null);
        assertEquals("1989-09-26 11:15:00 Smith", appointment.getInformation());

        Appointment appointment2 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), null, person1);
        assertEquals("1989-09-26 11:15:00", appointment2.getInformation());

        Appointment appointment3 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, person1);
        assertEquals("1989-09-26 11:15:00 Smith", appointment3.getInformation());
    }

    @Test
    void getInformationNullInNames() {
        String dateString = "1989-09-26";
        String timeString = "11:15:00";
        Person person = new Person();
        person.setName(null);
        person.setSurname(null);

        Appointment appointment = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, null);
        assertEquals("1989-09-26 11:15:00", appointment.getInformation());

        Appointment appointment2 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), null, person);
        assertEquals("1989-09-26 11:15:00", appointment2.getInformation());

        Appointment appointment3 = new Appointment(0, Date.valueOf(dateString), Time.valueOf(timeString), person, person);
        assertEquals("1989-09-26 11:15:00", appointment3.getInformation());
    }
}