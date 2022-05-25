package menu;

import models.Person;
import queries.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class AllDoctorsMenu extends Menu {
    ArrayList<Person> doctors;

    public AllDoctorsMenu(Query query, Person loggedUser) {
        super(query, loggedUser);
    }

    public void printDoctorsList() throws SQLException {
        doctors = query.getAllDoctors();
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println("* " + (i + 1) + ". " + doctors.get(i).getFullName());
        }
    }

    @Override
    public void print() throws SQLException {
        System.out.println("******* ZOZNAM DOKTOROV *******");
        printDoctorsList();
        System.out.println("* 0. Späť");
        System.out.println("******************************");
    }

    @Override
    public void handle(String option) throws SQLException {
        if (loggedUser == null) {
            System.out.println("NOBODY IS LOGGED");
            return;
        }
        if (Integer.parseInt(option) > 0 && Integer.parseInt(option) <= doctors.size()) {
            DoctorInfoMenu menu = new DoctorInfoMenu(query, loggedUser, doctors.get(Integer.parseInt(option) - 1).getId());
            menu.run();
            return;
        }
        if (Integer.parseInt(option) == 0) {
            exit();
            return;
        }

        System.out.println("INCORRECT COMMAND");

    }
}
