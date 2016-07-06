package sample;

import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;

public class Person {
    private final SimpleStringProperty firstName =
            new SimpleStringProperty("");
    private final SimpleStringProperty lastName = new SimpleStringProperty("");
    private final SimpleStringProperty studentNumber =
            new SimpleStringProperty("");
    private final SimpleStringProperty mark = new SimpleStringProperty("");
    private final SimpleStringProperty outliers = new SimpleStringProperty("");
    private final SimpleStringProperty fuck = new SimpleStringProperty("");

    public Person(String firstName, String lastName, String studentNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setStudentNumber(studentNumber);
        setMark("No Mark");
        setOutliers("");
    }
    public Person(String firstName, String lastName, String studentNumber,
                  String mark, String outlier) {
        setFirstName(firstName);
        setLastName(lastName);
        setStudentNumber(studentNumber);
        setMark(mark);
        setOutliers(outlier);
    }


    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String fName) {
        lastName.set(fName);
    }

    public String getStudentNumber() {
        return studentNumber.get();
    }

    public void setStudentNumber(String fName) {
        studentNumber.set(fName);
    }
    public String getMark() {
        try {
            if (!mark.get().equals("No Mark")) {
                DecimalFormat format = new DecimalFormat("###");
                return format.format(Double.parseDouble(mark.get()));
            }
        }
        catch (NumberFormatException e) {
            return mark.get();
        }
        return mark.get();
    }
    public String getMarkDecimal() {
        return mark.get();
    }
    public void setMark (String fName) {
        mark.set(fName);
    }
    public String getOutliers () {
        return outliers.get();
    }
    public void setOutliers (String fName) {
        outliers.set(fName);
    }
}