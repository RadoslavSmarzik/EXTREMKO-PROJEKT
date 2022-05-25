package menu;

import models.Appointment;
import models.Diagnosis;
import models.Person;
import queries.Query;

import java.util.*;
import java.sql.SQLException;

public class PatientMenu extends Menu {

    public PatientMenu(Query query, Person loggedUser) {
        super(query, loggedUser);
    }

    @Override
    public void print() {
        System.out.println("***************************");
        System.out.println("******* HLAVNÉ MENU *******");
        System.out.println("***************************");
        System.out.println("* 1. Moje stretnutia      *");
        System.out.println("* 2. Moje diagnózy        *");
        System.out.println("* 3. Zoznam doktorov      *");
        System.out.println("* 0. Odhlásenie           *");
        System.out.println("***************************");
    }

    @Override
    public void handle(String option) throws SQLException {

        if (loggedUser == null) {
            System.out.println("Nikto nie je prihlásený.");
            return;
        }

        if (option.equals("1")) {
            List<Appointment> appointmentList = query.getPatientAppointments(loggedUser.getId());
            for (Appointment appointment : appointmentList) {
                System.out.println(appointment.getInformation());
            }

        } else if (option.equals("2")) {
            List<Diagnosis> diagnosisList = query.getDiagnosis(loggedUser.getId());
            for (Diagnosis diagnosis : diagnosisList) {
                System.out.println(diagnosis.getInformation());
            }

        } else if (option.equals("3")) {
            AllDoctorsMenu menu = new AllDoctorsMenu(query, loggedUser);
            menu.run();

        } else if (option.equals("0")) {
            exit();

        } else {
            System.out.println("INCORRECT COMMAND");
        }


    }


}
