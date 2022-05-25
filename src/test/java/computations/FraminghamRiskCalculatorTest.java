package computations;

import models.Form;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class FraminghamRiskCalculatorTest {

    @Test
    void getResultFormNull() {
        Throwable exception = assertThrows(InvalidParameterException.class, () -> {
            FraminghamRiskCalculator framinghamRiskCalculator2 = new FraminghamRiskCalculator();
            framinghamRiskCalculator2.getResult(null);
        });
        assertEquals("Nastala chyba, dotazník nie je vyplnený!", exception.getMessage());
    }

    @Test
    void getResultFormNotFilled() {
        Throwable exception = assertThrows(InvalidParameterException.class, () -> {
            Form form = new Form();
            FraminghamRiskCalculator framinghamRiskCalculator2 = new FraminghamRiskCalculator();
            framinghamRiskCalculator2.getResult(form);
        });
        assertEquals("Nastala chyba, dotazník nie je vyplnený!", exception.getMessage());
    }

    @Test
    void getResultFormNotComplete() {
        Throwable exception = assertThrows(InvalidParameterException.class, () -> {
            Form form = new Form();
            form.setWoman(true);
            form.setHDLCholesterol(10);
            form.setAge(55);
            FraminghamRiskCalculator framinghamRiskCalculator2 = new FraminghamRiskCalculator();
            framinghamRiskCalculator2.getResult(form);
        });
        assertEquals("Nastala chyba, dotazník nie je vyplnený!", exception.getMessage());
    }

    @Test
    void getResultWomanOld() {
        Form form = new Form();
        form.setAge(79);
        form.setWoman(true);
        form.setBloodPressureTreatment(false);
        form.setSystolicBloodPressure(50);
        form.setTotalCholesterol(193.35);
        form.setHDLCholesterol(1);
        form.setSmoker(true);
        form.setHeight(167);
        form.setWeight(60);
        form.setStrokeVolume(70);
        form.setHeartRate(80);

        FraminghamRiskCalculator framinghamRiskCalculator = new FraminghamRiskCalculator();
        assertEquals(4.06, framinghamRiskCalculator.getRiskScoreForWoman(form), 0.1);
        assertEquals(51.21, framinghamRiskCalculator.getResult(form), 1.0);
    }

    @Test
    void getResultWomanYoung() {
        Form form = new Form();
        form.setAge(55);
        form.setWoman(true);
        form.setBloodPressureTreatment(false);
        form.setSystolicBloodPressure(50);
        form.setTotalCholesterol(193.35);
        form.setHDLCholesterol(1);
        form.setSmoker(true);
        form.setHeight(167);
        form.setWeight(60);
        form.setStrokeVolume(70);
        form.setHeartRate(80);

        FraminghamRiskCalculator framinghamRiskCalculator = new FraminghamRiskCalculator();
        assertEquals(3.25, framinghamRiskCalculator.getRiskScoreForWoman(form), 0.1);
        assertEquals(27.45, framinghamRiskCalculator.getResult(form), 1.0);
    }

    @Test
    void getResultManOld() {
        Form form = new Form();
        form.setAge(79);
        form.setWoman(false);
        form.setBloodPressureTreatment(false);
        form.setSystolicBloodPressure(50);
        form.setTotalCholesterol(193.35);
        form.setHDLCholesterol(1);
        form.setSmoker(false);
        form.setHeight(167);
        form.setWeight(60);
        form.setStrokeVolume(70);
        form.setHeartRate(80);

        FraminghamRiskCalculator framinghamRiskCalculator = new FraminghamRiskCalculator();
        assertEquals(3.44, framinghamRiskCalculator.getRiskScoreForMan(form), 0.1);
        assertEquals(85.36, framinghamRiskCalculator.getResult(form), 1.0);
    }

    @Test
    void getResultManYoung() {
        Form form = new Form();
        form.setAge(55);
        form.setWoman(false);
        form.setBloodPressureTreatment(false);
        form.setSystolicBloodPressure(50);
        form.setTotalCholesterol(193.35);
        form.setHDLCholesterol(1);
        form.setSmoker(true);
        form.setHeight(167);
        form.setWeight(60);
        form.setStrokeVolume(70);
        form.setHeartRate(80);

        FraminghamRiskCalculator framinghamRiskCalculator = new FraminghamRiskCalculator();
        assertEquals(2.99, framinghamRiskCalculator.getRiskScoreForMan(form), 0.1);
        assertEquals(70.77, framinghamRiskCalculator.getResult(form), 1.0);
    }

    @Test
    void getResultsInterpretationIncorrectValue() {
        FraminghamRiskCalculator framinghamRiskCalculator = new FraminghamRiskCalculator();
        assertEquals("Mimo intervalu hodnot (0 - 100%).", framinghamRiskCalculator.getResultsInterpretation(101));
        assertEquals("Mimo intervalu hodnot (0 - 100%).", framinghamRiskCalculator.getResultsInterpretation(-1));
    }

    @Test
    void getResultsInterpretationCorrectValue() {
        FraminghamRiskCalculator framinghamRiskCalculator = new FraminghamRiskCalculator();
        assertEquals("Riziko kardiovaskularneho ochorenia na najblizsich 10 rokov je nizke - 5.0%.", framinghamRiskCalculator.getResultsInterpretation(5));
        assertEquals("Riziko kardiovaskularneho ochorenia na najblizsich 10 rokov je mierne - 12.5%.", framinghamRiskCalculator.getResultsInterpretation(12.5));
        assertEquals("Riziko kardiovaskularneho ochorenia na najblizsich 10 rokov je vysoke - 25.0%.", framinghamRiskCalculator.getResultsInterpretation(25));
    }

    @Test
    void ageInEquationWithSmokingForWomen() {
        FraminghamRiskCalculator framinghamRiskCalculator = new FraminghamRiskCalculator();

        assertEquals(42, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(42, true));
        assertEquals(70, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(70, true));
        assertEquals(78, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(78, true));
        assertEquals(78, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(80, true));
        assertEquals(78, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(99, true));
        assertEquals(0, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(0, true));
        assertEquals(-100, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(-100, true));
        assertEquals(78, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(2147483647, true));

    }

    @Test
    void ageInEquationWithSmokingForMen() {
        FraminghamRiskCalculator framinghamRiskCalculator = new FraminghamRiskCalculator();

        assertEquals(42, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(42, false));
        assertEquals(70, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(70, false));
        assertEquals(70, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(78, false));
        assertEquals(70, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(99, false));
        assertEquals(0, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(0, false));
        assertEquals(-100, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(-100, false));
        assertEquals(70, framinghamRiskCalculator.getAgeUsedInEquationWithSmoking(2147483647, false));
    }
}