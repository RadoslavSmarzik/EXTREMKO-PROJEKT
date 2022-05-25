package queries;

import models.Appointment;
import dbConnect.DbContext;

import models.Diagnosis;
import models.Disease;
import models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query {

    public List<Appointment> getDoctorAppointments(int doctorId) throws SQLException {

        List<Appointment> appointments = new ArrayList<>();

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM appointment, person WHERE doctor_id = ? AND patient_id is not null AND person.id = patient_id")){
            statement.setInt(1, doctorId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("id");
                Date date = result.getDate("appointment_date");
                Time time = result.getTime("appointment_time");
                Person doctor = new Person();
                doctor.setName(result.getString("name"));
                doctor.setSurname(result.getString("surname"));
                Appointment appointment = new Appointment(id, date, time, doctor, null);
                appointments.add(appointment);
            }
        }

        return appointments;
    }

    public List<Appointment> getPatientAppointments(int patientId) throws SQLException {

        List<Appointment> appointments = new ArrayList<>();

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM appointment, person WHERE patient_id = ? AND person.id = doctor_id")){
            statement.setInt(1, patientId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("id");
                Date date = result.getDate("appointment_date");
                Time time = result.getTime("appointment_time");
                Person doctor = new Person();
                doctor.setName(result.getString("name"));
                doctor.setSurname(result.getString("surname"));
                Appointment appointment = new Appointment(id, date, time, doctor, null);
                appointments.add(appointment);
            }
        }

        return appointments;
    }

    public Person getPersonById(int userId) throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM person WHERE id = ?")){
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return null;
            }
            Person person = new Person();
            person.setAddress(resultSet.getString("address"));
            person.setBirthday(resultSet.getDate("birthday"));
            person.setCity(resultSet.getString("city"));
            person.setId(resultSet.getInt("id"));
            person.setInformation(resultSet.getString("information"));
            person.setDoctor(resultSet.getBoolean("isdoctor"));
            person.setLogin(resultSet.getString("login"));
            person.setName(resultSet.getString("name"));
            person.setPassword(resultSet.getString("password"));
            person.setSurname(resultSet.getString("surname"));
            return new Person();
        }
    }

    public List<Diagnosis> getDiagnosis(int userId) throws SQLException {

        List<Diagnosis> allDiagnosis = new ArrayList<>();

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM diagnosis, disease WHERE patient_id = ? AND disease_id = disease.id")){
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Disease disease = new Disease(0, result.getString("name"));
                Diagnosis diagnosis = new Diagnosis(result.getInt("id"), disease, null, result.getString("treatment"));
                allDiagnosis.add(diagnosis);
            }
        }

        return allDiagnosis;
    }

    public ArrayList<Person> getAllDoctors() throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM person WHERE isdoctor = true")){
            ArrayList<Person> doctors = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Person doctor = new Person();
                doctor.setId(result.getInt("id"));
                doctor.setName(result.getString("name"));
                doctor.setSurname(result.getString("surname"));
                doctor.setAddress(result.getString("address"));
                doctor.setBirthday(result.getDate("birthday"));
                doctor.setCity(result.getString("city"));
                doctor.setInformation(result.getString("information"));
                doctor.setDoctor(result.getBoolean("isDoctor"));
                doctors.add(doctor);
            }
            return doctors;
        }

    }


    public ArrayList<Appointment> getFreeAppointments(int doctorId) throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM appointment WHERE doctor_id = ? AND patient_id is null")){
            statement.setInt(1, doctorId);
            ResultSet result = statement.executeQuery();
            ArrayList<Appointment> appointments = new ArrayList<>();

            while(result.next()){
                Appointment appointment = new Appointment(result.getInt("id"), result.getDate("appointment_date"), result.getTime("appointment_time"), null, null);
                appointments.add(appointment);
            }
            return appointments;
        }
    }

    public void makeAppointment(int userId, int appointmentId) throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("UPDATE appointment SET patient_id = ? WHERE id = ?")){
            statement.setInt(1, userId);
            statement.setInt(2, appointmentId);
            statement.executeUpdate();
        }

    }

    public void putFreeAppointment(int userId, Date date, Time time) throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("INSERT INTO appointment (appointment_date, appointment_time, doctor_id, patient_id) VALUES (?, ?, ?, null)")){
            statement.setDate(1, date);
            statement.setTime(2, time);
            statement.setInt(3, userId);
            statement.execute();
        }
    }


    public void printInformationAboutPatients(List<Person> patients){
        if(patients == null || patients.size() == 0){
            System.out.println("You have not patients.");
            return;
        }
        for(Person person : patients){
            if(person.getFullName().strip().isEmpty()){
                System.out.println("Only login is available: " + person.getLogin() + ", " + person.getBirthday() + ", " + person.getAddress() + ", " + person.getCity());
            } else {
                System.out.println(person.getFullName() + ", " + person.getBirthday() + ", " + person.getAddress() + ", " + person.getCity());
            }
        }
    }

    public Person getUser(String login, String password) throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM Person WHERE login = ? AND password = ?")) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return null;
            }
            Person person = new Person();
            person.setAddress(resultSet.getString("address"));
            person.setBirthday(resultSet.getDate("birthday"));
            person.setCity(resultSet.getString("city"));
            person.setId(resultSet.getInt("id"));
            person.setInformation(resultSet.getString("information"));
            person.setDoctor(resultSet.getBoolean("isdoctor"));
            person.setLogin(resultSet.getString("login"));
            person.setName(resultSet.getString("name"));
            person.setPassword(resultSet.getString("password"));
            person.setSurname(resultSet.getString("surname"));
            return person;
        }

    }

    public ArrayList<Person> getMyPatients(int userId) throws SQLException {

        ArrayList<Person> allPatients = new ArrayList();
        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM Doctor_Patient, Person WHERE doctor_id = ? AND patient_id = person.id")){
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Person person = new Person();
                person.setId(result.getInt("patient_id"));
                person.setName(result.getString("name"));
                person.setSurname(result.getString("surname"));
                allPatients.add(person);
            }
        }
        return allPatients;
    }

    public ArrayList<Disease> getDiseases() throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("SELECT * FROM disease")){
            ResultSet result = statement.executeQuery();
            ArrayList<Disease> allDisease = new ArrayList<>();
            while(result.next()){
                allDisease.add(new Disease(result.getInt("id"), result.getString("name")));
            }
            return allDisease;
        }
    }

    public void addPatientDisease(int userId, int diseaseId, String treatment) throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("INSERT INTO diagnosis (patient_id, disease_id, treatment) VALUES (?, ?, ?)")){
            statement.setInt(1, userId);
            statement.setInt(2, diseaseId);
            statement.setString(3, treatment);
            statement.execute();
        }
    }

    public void updatePersonInformation(int userId, String information) throws SQLException {

        try(PreparedStatement statement = DbContext.getConnection().prepareStatement("UPDATE person SET information = ? WHERE id = ?")){
            statement.setString(1, information);
            statement.setInt(2, userId);
            statement.execute();
        }
    }

}
