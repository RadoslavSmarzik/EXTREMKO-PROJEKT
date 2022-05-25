package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagnosisTest {

    @Test
    void getInformation() {
        Disease disease = new Disease(1, "chrípka");
        Diagnosis d = new Diagnosis(1, disease, null, "spánok");
        assertEquals("chrípka, liečba: spánok", d.getInformation());

    }

    @Test
    void getInformationSpacesBetween() {
        Disease disease = new Disease(1,"KAŠEL     ");
        Diagnosis d = new Diagnosis(1, disease, null, "    tablety");
        assertEquals("KAŠEL, liečba: tablety", d.getInformation());

    }

    @Test
    void getInformationSpacesFromBothSides() {
        Disease disease = new Disease(1,"      Nádcha   ");
        Diagnosis d = new Diagnosis(1, disease, null, "     sirup   ");
        assertEquals("Nádcha, liečba: sirup", d.getInformation());

    }

    @Test
    void getInformationDiseaseNull() {
        Diagnosis d = new Diagnosis(1, null, null, "     sirup   ");
        assertEquals("", d.getInformation());

    }

    @Test
    void getInformationDiseaseNameEmpty() {
        Disease disease = new Disease(1,"");
        Diagnosis d = new Diagnosis(1,disease , null, " sirup ");
        assertEquals("", d.getInformation());

    }

    @Test
    void getInformationDiseaseNameNull() {
        Disease disease = new Disease(1,null);
        Diagnosis d = new Diagnosis(1,disease , null, "  sirup   ");
        assertEquals("", d.getInformation());
    }

    @Test
    void getInformationDiseaseNameSpaces() {
        Disease disease = new Disease(1,"             ");
        Diagnosis d = new Diagnosis(1,disease , null, "  sirup   ");
        assertEquals("", d.getInformation());
    }

    @Test
    void getInformationDiseaseTreatmentNull() {
        Disease disease = new Disease(1,"Chrípka");
        Diagnosis d = new Diagnosis(1,disease , null, null);
        assertEquals("Chrípka", d.getInformation());
    }

    @Test
    void getInformationDiseaseTreatmentEmpty() {
        Disease disease = new Disease(1,"   Chrípka   ");
        Diagnosis d = new Diagnosis(1,disease , null, "");
        assertEquals("Chrípka", d.getInformation());
    }

    @Test
    void getInformationDiseaseTreatmentSpaces() {
        Disease disease = new Disease(1,"       Chrípka   ");
        Diagnosis d = new Diagnosis(1,disease , null, "          ");
        assertEquals("Chrípka", d.getInformation());
    }
}