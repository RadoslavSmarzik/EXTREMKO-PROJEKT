package menu;

import models.Person;
import queries.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class MyPatientsMenu extends Menu {
    ArrayList<Person> patients;

    public MyPatientsMenu(Query query, Person loggedUser) {
        super(query, loggedUser);
    }

    public void printPatientsList() throws SQLException {
        patients = query.getMyPatients(loggedUser.getId());
        for (int i = 0; i < patients.size(); i++) {
            System.out.println("* " + (i + 1) + ". " + patients.get(i).getName() + " " + patients.get(i).getSurname());
        }
    }

    @Override
    public void print() throws SQLException {
        System.out.println("******* ZOZNAM PACIENTOV *******");
        printPatientsList();
        System.out.println("* 0. Späť");
        System.out.println("******************************");
    }

    @Override
    public void handle(String option) throws SQLException {
        if (loggedUser == null) {
            System.out.println("NOBODY IS LOGGED");
            return;
        }
        if (Integer.parseInt(option) > 0 && Integer.parseInt(option) <= patients.size()) {
            PatientInfoMenu menu = new PatientInfoMenu(query, loggedUser, patients.get(Integer.parseInt(option) - 1).getId());
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
