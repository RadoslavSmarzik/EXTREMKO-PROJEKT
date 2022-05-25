package computations;

import models.Form;
import java.lang.Math;

import java.security.InvalidParameterException;

public class FraminghamRiskCalculator implements Computation {


    private double bpTreatment;
    private double smoker;
    private String NOT_FILLED_FORM = "Nastala chyba, dotazník nie je vyplnený!";

    private boolean isFormCorrect(Form form) throws InvalidParameterException {
        if(form == null || !form.isFilled()){
            throw new InvalidParameterException(NOT_FILLED_FORM);
        }
        return true;
    }

    private void setBooleanValues(Form form){
        bpTreatment = getDouble(form.isBloodPressureTreatment());
        smoker = getDouble(form.isSmoker());
    }


    @Override
    public double getResult(Form form) throws InvalidParameterException {

        double riskScore;

        try{
            isFormCorrect(form);
        } catch (InvalidParameterException e){
            throw e;
        }

        if(form.isWoman()){
            riskScore = getRiskScoreForWoman(form);
            return getDeathProbability(riskScore, FraminghamConst.baseInDeathProbabilityForWomen) * 100;
        }

        riskScore = getRiskScoreForMan(form);
        return getDeathProbability(riskScore, FraminghamConst.baseInDeathProbabilityForMen) * 100;
    }

    @Override
    public String getResultsInterpretation(double result) {
        if(result < 0 || result > 100){
            return "Mimo intervalu hodnot (0 - 100%).";
        }
        if(result < 10){
            return "Riziko kardiovaskularneho ochorenia na najblizsich 10 rokov je nizke - " + result + "%.";
        }
        if(result < 19){
            return "Riziko kardiovaskularneho ochorenia na najblizsich 10 rokov je mierne - " + result + "%.";
        }
        return "Riziko kardiovaskularneho ochorenia na najblizsich 10 rokov je vysoke - " + result + "%.";
    }

    public double getRiskScoreForMan(Form form){
        try{
            isFormCorrect(form);
        } catch (InvalidParameterException e){
            throw e;
        }

        setBooleanValues(form);

        double age = getLogProduct(FraminghamConst.ageM, form.getAge());
        double cholesterol = getLogProduct(FraminghamConst.totalCholesterolM, form.getTotalCholesterol());
        double hdl = getLogProduct(FraminghamConst.HDLCholesterolM, form.getHDLCholesterol());
        double systolicBP = getLogProduct(FraminghamConst.systolicBPM, form.getSystolicBloodPressure());
        double treatmentBP = getProduct(FraminghamConst.treatmentBPM, bpTreatment);
        double smoker2 = getProduct(FraminghamConst.smokerM, smoker);
        double age2 = getLogProduct(FraminghamConst.ageCholesterolM, form.getAge());
        double ageCholesterol = getLogProduct(age2, form.getTotalCholesterol());
        double correctAge = getAgeUsedInEquationWithSmoking(form.getAge(), false);
        double ageSmoker = getLogProduct(FraminghamConst.ageSmokerM * smoker, correctAge);
        double twiceAge = getLogProduct(FraminghamConst.ageTwiceM, form.getAge());
        double twiceAge2 = getLogProduct(twiceAge, form.getAge());

        return age + cholesterol + hdl + systolicBP + treatmentBP + smoker2 + ageCholesterol + ageSmoker + twiceAge2 - FraminghamConst.endM;
    }

    public double getRiskScoreForWoman(Form form){
        try{
            isFormCorrect(form);
        } catch (InvalidParameterException e){
            throw e;
        }

        setBooleanValues(form);

        double age = getLogProduct(FraminghamConst.ageW, form.getAge());
        double cholesterol = getLogProduct(FraminghamConst.totalCholesterolW, form.getTotalCholesterol());
        double hdl = getLogProduct(FraminghamConst.HDLCholesterolW, form.getHDLCholesterol());
        double systolicBP = getLogProduct(FraminghamConst.systolicBPW, form.getSystolicBloodPressure());
        double treatmentBP = getProduct(FraminghamConst.treatmentBPW, bpTreatment);
        double smoker2 = getProduct(FraminghamConst.smokerW, smoker);
        double age2 = getLogProduct(FraminghamConst.ageCholesterolW, form.getAge());
        double ageCholesterol = getLogProduct(age2, form.getTotalCholesterol());
        double correctAge = getAgeUsedInEquationWithSmoking(form.getAge(), true);
        double ageSmoker = getLogProduct(FraminghamConst.ageSmokerW * smoker, correctAge);
        return age + cholesterol + hdl + systolicBP + treatmentBP + smoker2 + ageCholesterol + ageSmoker - FraminghamConst.endW;
    }

    private double getDeathProbability(double riskScore, double baseAccordingToSex){
        return 1 -  Math.pow(baseAccordingToSex, Math.exp(riskScore));
    }

    public int getAgeUsedInEquationWithSmoking(int age, Boolean isWoman){
        if(isWoman){
            return Math.min(age, FraminghamConst.maxAgeForWomen);
        }
        return Math.min(age, FraminghamConst.maxAgeForMen);
    }

    private double getLogProduct(double constant, double patientInfo){
        return constant * Math.log(patientInfo);
    }

    private double getProduct(double constant, double patientInfo){
        return constant * patientInfo;
    }

    private double getDouble(Boolean value){
        if(value){
            return 1;
        }
        return 0;
    }
}
