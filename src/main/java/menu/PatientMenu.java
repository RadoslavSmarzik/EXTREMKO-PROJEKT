package menu;

import computations.CardiacIndexCalculator;
import computations.Computation;
import computations.FraminghamRiskCalculator;
import computations.SixMinuteWalkTestCalculator;
import models.Appointment;
import models.Diagnosis;
import models.Form;
import models.Person;
import queries.Query;

import java.security.InvalidParameterException;
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
        System.out.println("* 4. Vyplň dotazník       *");
        System.out.println("* 0. Odhlásenie           *");
        System.out.println("***************************");
    }

    @Override
    public void handle(String option) throws SQLException {

        if (loggedUser == null) {
            System.out.println("Nikto nie je prihlaseny.");
            return;
        }

        if(option == null || query == null){
            return;
        }

        if (option.equals("1")) {
            getAppointments();

        } else if (option.equals("2")) {
            getDiagnosis();

        } else if (option.equals("3")) {
            AllDoctorsMenu menu = new AllDoctorsMenu(query, loggedUser);
            menu.run();

        } else if (option.equals("4")) {
            evaluateForm();

        } else if (option.equals("0")) {
            exit();

        } else {
            System.out.println("Neznamy prikaz.");
        }
    }

    private void getAppointments() throws SQLException {
        List<Appointment> appointmentList = query.getPatientAppointments(loggedUser.getId());
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment.getInformation());
        }
    }

    private void getDiagnosis() throws SQLException {
        List<Diagnosis> diagnosisList = query.getDiagnosis(loggedUser.getId());
        for (Diagnosis diagnosis : diagnosisList) {
            System.out.println(diagnosis.getInformation());
        }
    }

    public void evaluateForm(){
        Form form = new Form();
        System.out.println(form.isFilled());
        form.getQuestions();
        try{
            System.out.println("FOOOORM");
            System.out.println(form);
            System.out.println(form.isFilled());
            System.out.println(form.getAge());
            System.out.println(form.getHeight());
            System.out.println(form.getWeight());
            System.out.println(form.getTotalCholesterol());
            System.out.println(form.getHDLCholesterol());
            System.out.println(form.getSystolicBloodPressure());
            System.out.println(form.getHeartRate());
            System.out.println(form.isBloodPressureTreatment());
            System.out.println(form.isWoman());
            System.out.println(form.isSmoker());
            System.out.println(form.getStrokeVolume());

            String result1 = getResultFromTest(new FraminghamRiskCalculator(), form);
            String result2 = getResultFromTest(new CardiacIndexCalculator(), form);
            String result3 = getResultFromTest(new SixMinuteWalkTestCalculator(), form);
            System.out.println(result1);
            System.out.println(result2);
            System.out.println(result3);
        } catch (InvalidParameterException e){
            System.out.println("Nie všetky otazky su zodpovedane v dotazniku.");
        }




    }

    private String getResultFromTest(Computation computation, Form form){
        System.out.println("FORM");
        System.out.println(form);

        double result = computation.getResult(form);;
        return computation.getResultsInterpretation(result);
    }

}
