package menu;

import models.Appointment;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import queries.Query;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DoctorMenuTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void handleNullsInParameter() throws SQLException {
        Menu menu = new DoctorMenu(null, null);
        menu.handle(null);
        String result = "Nikto nie je prihlaseny.";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleLoggedButNoQuery() throws SQLException {
        Menu menu = new DoctorMenu(null, new Person());
        menu.handle(null);
        String result = "";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleLoggedQueryWithoutOption() throws SQLException {
        Menu menu = new DoctorMenu(new Query(), new Person());
        menu.handle(null);
        String result = "";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleUnknownOption() throws SQLException {
        Menu menu = new DoctorMenu(new Query(), new Person());
        menu.handle("aaa");
        String result = "Neznamy prikaz.";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleUnknownOption2() throws SQLException {
        Menu menu = new DoctorMenu(new Query(), new Person());
        String result = "Neznamy prikaz.";
        menu.handle("1   ");
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleExit() throws SQLException {
        Menu menu = new DoctorMenu(new Query(), new Person());
        String result = "";
        menu.handle("0");
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void isCorrectInformationIncorrectValues() {
        DoctorMenu doctorMenu = new DoctorMenu(new Query(), new Person());
        assertFalse(doctorMenu.isCorrectInformation(null));
        assertFalse(doctorMenu.isCorrectInformation(""));
        assertFalse(doctorMenu.isCorrectInformation("                      "));
        String temp = "";
        for(int i = 0; i < 10002; i++){
            temp += "a";
        }
        assertFalse(doctorMenu.isCorrectInformation(temp));
    }

    @Test
    void isCorrectInformationCorrectValues() {
        DoctorMenu doctorMenu = new DoctorMenu(new Query(), new Person());
        assertTrue(doctorMenu.isCorrectInformation("null   "));
        String temp = "a";
        for(int i = 0; i < 10001; i++){
            temp += " ";
        }
        assertTrue(doctorMenu.isCorrectInformation(temp));
    }

    @Test
    void putFreeAppointmentIncorrectDate() throws SQLException {
        String date1 = "eifmiem ";
        String date2 = "05-14-1997";
        String date3 = "51351";
        String date4 = "1997-11-03";
        String time = "10:30:00";

        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, new Person());
        Mockito.doNothing().when(query).putFreeAppointment(0, Date.valueOf(date4), Time.valueOf(time));

        doctorMenu.putFreeAppointment(null, time);
        String result = "Zly format pre termin.";
        assertEquals(result, outputStreamCaptor.toString().trim());

        doctorMenu.putFreeAppointment(date1, time);
        result += "\nZly format pre termin.";
        assertEquals(result, outputStreamCaptor.toString().trim());

        doctorMenu.putFreeAppointment(date2, time);
        result += "\nZly format pre termin.";
        assertEquals(result, outputStreamCaptor.toString().trim());

        doctorMenu.putFreeAppointment(date3, time);
        result += "\nZly format pre termin.";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void putFreeAppointmentIncorrectTime() throws SQLException {
        String time1 = "eifmiem ";
        String time2 = "155:10:10";
        String time3 = "51351";
        String date = "1997-11-03";
        String time = "10:30:00";

        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, new Person());
        Mockito.doNothing().when(query).putFreeAppointment(0, Date.valueOf(date), Time.valueOf(time));

        doctorMenu.putFreeAppointment(date, time1);
        String result = "Zly format pre termin.";
        assertEquals(result, outputStreamCaptor.toString().trim());

        doctorMenu.putFreeAppointment(date, time2);
        result += "\nZly format pre termin.";
        assertEquals(result, outputStreamCaptor.toString().trim());

        doctorMenu.putFreeAppointment(date, time3);
        result += "\nZly format pre termin.";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void putFreeAppointmentCorrectValue() throws SQLException {
        String date = "1997-11-03";
        String time = "10:30:00";

        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, new Person());
        Mockito.doNothing().when(query).putFreeAppointment(0, Date.valueOf(date), Time.valueOf(time));

        doctorMenu.putFreeAppointment(date, time);
        String result = "Volny termin uspesne pridany.";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void putFreeAppointmentPersonIsNull() throws SQLException {
        String date = "1997-11-03";
        String time = "10:30:00";

        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, null);
        Mockito.doNothing().when(query).putFreeAppointment(new Person().getId(), Date.valueOf(date), Time.valueOf(time));

        doctorMenu.putFreeAppointment(date, time);
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void putFreeAppointmentNullQuery() throws SQLException {
        String date = "1997-11-03";
        String time = "10:30:00";

        DoctorMenu doctorMenu = new DoctorMenu(null, new Person());
        doctorMenu.putFreeAppointment(date, time);
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void updateInformationNullPerson() throws SQLException {
        String info = "   ";

        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, null);
        Mockito.doNothing().when(query).updatePersonInformation(new Person().getId(), info);

        doctorMenu.updateInformation(info);
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void updateInformationNullQuery() throws SQLException {
        String info = "   ";

        DoctorMenu doctorMenu = new DoctorMenu(null, new Person());
        doctorMenu.updateInformation(info);
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void updateInformationIncorrectInformation() throws SQLException {
        String info = "   ";
        Person person = new Person();
        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, person);
        Mockito.doNothing().when(query).updatePersonInformation(person.getId(), info);

        doctorMenu.updateInformation(info);
        assertEquals("Nespravna dlzka spravy.", outputStreamCaptor.toString().trim());
    }

    @Test
    void updateInformationCorrectInformation() throws SQLException {
        String info = "ahoj";

        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, null);
        Mockito.doNothing().when(query).updatePersonInformation(new Person().getId(), info);

        doctorMenu.updateInformation(info);
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void getAllAppointmentsReturnsNull() throws SQLException {
        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, null);
        Mockito.when(query.getDoctorAppointments(0)).thenReturn(null);

        doctorMenu.getAllAppointments();
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void getAllAppointmentsReturnsEmptyArray() throws SQLException {
        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, null);
        Mockito.when(query.getDoctorAppointments(0)).thenReturn(new ArrayList<>());

        doctorMenu.getAllAppointments();
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void getAllAppointments() throws SQLException {
        Query query = Mockito.mock(Query.class);
        DoctorMenu doctorMenu = new DoctorMenu(query, new Person());
        List<Appointment> appointments = new ArrayList<>();
        Person person = new Person();
        person.setSurname("Smith");
        appointments.add(new Appointment(0, Date.valueOf("2022-05-05"), Time.valueOf("11:00:00"), person, null));
        appointments.add(new Appointment(0, Date.valueOf("2023-05-05"), Time.valueOf("15:30:00"), person, person));

        Mockito.when(query.getDoctorAppointments(0)).thenReturn(appointments);

        doctorMenu.getAllAppointments();
        assertEquals("2022-05-05 11:00:00 Smith\n2023-05-05 15:30:00 Smith s Smith", outputStreamCaptor.toString().trim());
    }






}