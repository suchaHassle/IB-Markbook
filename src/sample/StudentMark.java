package sample;

import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;

public class StudentMark {
    private final SimpleStringProperty evaluationName = new
            SimpleStringProperty("");
    private final SimpleStringProperty weight = new SimpleStringProperty("");
    private final SimpleStringProperty mark = new SimpleStringProperty("");
    private final SimpleStringProperty outOf = new SimpleStringProperty("");
    private final SimpleStringProperty percent = new SimpleStringProperty("");
    private final SimpleStringProperty outlier = new SimpleStringProperty("");

    public StudentMark (String evaluationName, String weight, String mark,
                        String outOf, String outlier) {
        setEvaluationName(evaluationName);
        setWeight(weight);
        setMark(mark);
        setOutOf(outOf);
        DecimalFormat format = new DecimalFormat("###.#");
        double percentage = Double.parseDouble(getMark()) / Double
                .parseDouble(getOutOf()) * 100;
        setPercent(format.format(percentage));
        setOutlier(outlier);
    }
    public void setEvaluationName(String fName) {
        evaluationName.set(fName);
    }
    public void setMark (String fName) {
        mark.set(fName);
    }
    public void setWeight(String fName) {
        weight.set(fName);
    }
    public void setOutOf (String fName) {
        outOf.set(fName);
    }
    public void setPercent(String fName) {
        percent.set(fName);
    }
    public void setOutlier (String fName) {
        outlier.set(fName);
    }
    public String getEvaluationName() {
        return evaluationName.get();
    }
    public String getMark() {
        return mark.get();
    }
    public String getWeight() {
        return weight.get();
    }
    public String getOutOf() {
        return outOf.get();
    }
    public String getPercent() {
        return percent.get();
    }
    public String getOutlier() {
        return outlier.get();
    }
}
