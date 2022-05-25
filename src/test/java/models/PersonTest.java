package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void getFullName() {
        Person p = new Person();
        p.setName("Peter");
        p.setSurname("Maly");
        assertEquals("Peter Maly", p.getFullName());
    }

    @Test
    void getFullNameSpaces() {
        Person p = new Person();
        p.setName(" IVAN   ");
        p.setSurname("   VELKY   ");
        assertEquals("IVAN VELKY", p.getFullName());
    }

    @Test
    void getFullNameJustName() {
        Person p = new Person();
        p.setName("   Michal   ");
        assertEquals("Michal", p.getFullName());
    }

    @Test
    void getFullNameJustSurname() {
        Person p = new Person();
        p.setSurname("  Pekny   ");
        assertEquals("Pekny", p.getFullName());
    }

    @Test
    void getFullNameEmpty() {
        Person p = new Person();
        assertEquals("", p.getFullName());
    }

    @Test
    void getFullNameNameSpaces() {
        Person p = new Person();
        p.setName("          ");
        assertEquals("", p.getFullName());
    }

    @Test
    void getFullNameSurNameSpaces() {
        Person p = new Person();
        p.setSurname("          ");
        assertEquals("", p.getFullName());
    }

}