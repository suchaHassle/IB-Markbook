package sample;

import javafx.beans.property.SimpleStringProperty;

public class Mark {
    private final SimpleStringProperty evaluationName = new
            SimpleStringProperty("");
    private final SimpleStringProperty mark = new SimpleStringProperty("");

    public Mark (String evaluationName, String mark) {
        setEvaluationName(evaluationName);
        setMark(mark);
    }
    public void setEvaluationName(String fName) {
        evaluationName.set(fName);
    }
    public void setMark (String fName) {
        mark.set(fName);
    }
    public String getEvaluationName() {
        return evaluationName.get();
    }
    public String getMark() {
        return mark.get();
    }
}
