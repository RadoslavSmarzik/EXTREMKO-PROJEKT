package models;

public class Diagnosis {

    int id;
    Disease disease;
    Person patient;
    String treatment;

    public Diagnosis(int id, Disease disease, Person patient, String treatment){
        this.id = id;
        this.disease = disease;
        this.patient = patient;
        this.treatment = treatment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Person getPatient() {
        return patient;
    }

    public void setPatient(Person patient) {
        this.patient = patient;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getInformation(){
        if(disease == null || disease.getName().equals("")){
            return "";
        }
        if(treatment == null || treatment.strip().equals("")){
            return getDisease().getName();
        }
        return getDisease().getName() + ", treatment: " + getTreatment();
    }
}
