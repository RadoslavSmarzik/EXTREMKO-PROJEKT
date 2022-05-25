package menu;

import models.Appointment;
import models.Person;
import queries.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentsMenu extends Menu {
    int doctorId;
    ArrayList<Appointment> appointments;

    public AppointmentsMenu(Query query, Person loggedUser, int doctorId) {
        super(query, loggedUser);
        this.doctorId = doctorId;
    }

    public void printPatientsList() throws SQLException {
        appointments = query.getFreeAppointments(doctorId);
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println("* " + (i + 1) + ". " + appointments.get(i).getDate() + " - " + appointments.get(i).getTime());
        }
    }

    @Override
    public void print() throws SQLException {
        System.out.println("***** VOLNE TERMINY *****");
        printPatientsList();
        System.out.println("* 0. Späť");
        System.out.println("************************");
    }

    @Override
    public void handle(String option) throws SQLException {
        if (loggedUser == null) {
            System.out.println("NOBODY IS LOGGED");
            return;
        }
        if (Integer.parseInt(option) > 0 && Integer.parseInt(option) <= appointments.size()) {
            query.makeAppointment(loggedUser.getId(), appointments.get(Integer.parseInt(option) - 1).getId());
            return;
        }

        if (Integer.parseInt(option) == 0) {
            exit();
            return;
        }

        System.out.println("INCORRECT COMMAND");
    }
}
