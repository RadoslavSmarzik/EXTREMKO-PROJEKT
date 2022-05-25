package menu;

import models.Appointment;
import models.Person;
import queries.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class DoctorMenu extends Menu {

    private int maxLengthOfInfo = 10000;
    private int hours = 24;
    private int minutes = 60;
    private int seconds = 60;

    public DoctorMenu(Query query, Person loggedUser) {
        super(query, loggedUser);
    }

    @Override
    public void print() {
        System.out.println("***************************");
        System.out.println("******* HLAVNÉ MENU *******");
        System.out.println("***************************");
        System.out.println("* 1. Moje stretnutia      *");
        System.out.println("* 2. Vytvor voľný termín  *");
        System.out.println("* 3. Pacienti             *");
        System.out.println("* 4. Uprav môj popis      *");
        System.out.println("* 0. Odhlásenie           *");
        System.out.println("***************************");
    }

    @Override
    public void handle(String option) throws SQLException {

        if(loggedUser == null){

            System.out.println("Nikto nie je prihlaseny.");
            
            return;
        }

        if(option == null){
            return;
        }

        if(option.equals("1")){
            getAllAppointments();

        } else if(option.equals("2") && loggedUser.isDoctor()) {
            String dateString = loadDate();
            String timeString = loadTime();
            putFreeAppointment(dateString, timeString);

        } else if(option.equals("3")){
            MyPatientsMenu menu = new MyPatientsMenu(query, loggedUser);
            menu.run();

        } else if(option.equals("4")){
            String information = loadInformation();
            updateInformation(information);

        } else if(option.equals("0")){
            exit();

        } else {
            System.out.println("Neznamy prikaz.");
        }
    }

    private boolean areInitialValuesSet(){
        return loggedUser != null && query != null;
    }

    public void getAllAppointments() throws SQLException {
        if(!areInitialValuesSet()){
            return;
        }

        List<Appointment> appointmentList = query.getDoctorAppointments(loggedUser.getId());

        for (Appointment appointment : appointmentList) {
            System.out.println(appointment.getInformation());
        }
    }

    public void putFreeAppointment(String dateString, String timeString) throws SQLException {
        if(!areInitialValuesSet()){
            return;
        }

        Date date = convertToDate(dateString);
        Time time = convertToTime(timeString);
        if(date != null && time != null){
            query.putFreeAppointment(loggedUser.getId(), date, time);
            System.out.println("Volny termin uspesne pridany.");
        } else {
            System.out.println("Zly format pre termin.");
        }
    }

    public void updateInformation(String information) throws SQLException {
        if(!areInitialValuesSet()){
            return;
        }

        if(isCorrectInformation(information)){
            query.updatePersonInformation(loggedUser.getId(),information.strip());
        } else{
            System.out.println("Nespravna dlzka spravy.");
        }
    }

    private String loadDate(){
        System.out.println("Zadajte datum (yyyy-mm-dd)");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    private String loadTime(){
        System.out.println("Zadajte čas (hh:mm:ss)");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    private String loadInformation(){
        System.out.println("Zadajte informacie");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    public Date convertToDate(String date){
        try{
            return Date.valueOf(date);
        } catch (IllegalArgumentException exception){
            return null;
        }
    }

    public Time convertToTime(String time){
        try{
            Time result = Time.valueOf(time);
            if(isCorrectTimeAdditionalCheck(time)){
                return result;
            }
        } catch (IllegalArgumentException exception){
            return null;
        }
        return null;
    }

    private boolean isCorrectTimeAdditionalCheck(String time){
        String[] timeArray = time.split(":");
        if(timeArray.length == 3){
            int hours1 = Integer.parseInt(timeArray[0]);
            int minutes1 = Integer.parseInt(timeArray[1]);
            int seconds1 = Integer.parseInt(timeArray[2]);
            if(hours1 < hours && minutes1 < minutes && seconds1 < seconds){
                return true;
            }
        }
        return false;
    }

    public boolean isCorrectInformation(String information){
        if(information == null){
            return false;
        }

        String information1 = information.strip();
        if(information1.equals("") || information1.length() > maxLengthOfInfo){
            return false;
        }

        return true;
    }

}
