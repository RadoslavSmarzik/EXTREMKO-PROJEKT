package menu;

import models.Disease;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import queries.Query;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiseaseMenuTest {
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
    void handleNobodyLogged() throws SQLException {
        Menu menu = new DiseaseMenu(null, null, 1);
        menu.handle(null);
        String result = "Nikto nie je prihlaseny";
        assertEquals(result, outputStreamCaptor.toString().trim());

    }

    @Test
    void handleNullCommand() throws SQLException {
        Menu menu = new DiseaseMenu(null, new Person(), 1);
        menu.handle(null);
        String result = "Prikaz musi byt cislo";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleTextComand() throws SQLException {
        Menu menu = new DiseaseMenu(null, new Person(), 1);
        menu.handle("afafa");
        String result = "Prikaz musi byt cislo";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleNumberTextComand() throws SQLException {
        Menu menu = new DiseaseMenu(null, new Person(), 1);
        menu.handle("35a");
        String result = "Prikaz musi byt cislo";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleOutOfRangeCommandMinus() throws SQLException {
        Menu menu = new DiseaseMenu(null, new Person(), 1);
        menu.handle("-5");
        String result = "Neznamy prikaz";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleNoDisease() throws SQLException {
        Menu menu = new DiseaseMenu(null, new Person(), 1);
        menu.handle("1");
        String result = "Neznamy prikaz";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleZero() throws SQLException {
        Menu menu = new DiseaseMenu(null, new Person(), 1);
        menu.handle("0");
        String result = "";
        assertEquals(result, outputStreamCaptor.toString().trim());
    }

}