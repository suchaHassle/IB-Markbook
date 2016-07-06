/**
 * This is the controller for the student.fxml, it displays all the marks
 * and the student's average, converted IB score, and OSSD Converted
 */
package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class StudentController {

    @FXML private Label name;
    @FXML private Label studentNumber;
    @FXML private Label mark;
    @FXML private Label IBScore;
    @FXML private Label OSSDMark;
    @FXML private Button removeOutliers;
    @FXML private Button delete;
    @FXML private TableView tableView3;

    /**
     * Before the fxml file launches, the table is populated with all the
     * evaluations for the student
     *
     * @throws Exception
     */
    public void populateTable() throws Exception {
        name.setText(Student.studentFirstName + " " + Student.studentLastName);
        studentNumber.setText(Student.studentNumber);
        mark.setText(Student.mark);
        IBScore.setText(Student.convert());
        OSSDMark.setText(Student.ossdConvert());

        ObservableList<StudentMark> data = tableView3.getItems();
        data.removeAll(data);

        double[] mark = Outliers.determineEachEntry(Student.studentNumber,
                Student.className);

        for (int i = 0; i < Student.marks.size(); i+=4) {
            String outlier = "";
            if (mark[i/4] * -1 >= 0)
                outlier = "*";

            data.add(new StudentMark(Student.marks.get(i),
                    Student.marks.get(i+3), Student.marks.get(i+1),
                    Student.marks.get(i+2), outlier));
        }
    }

    /**
     * This is a button event handler that removes all entries that are
     * outliers and deletes them from the file for the student in the database
     *
     * @param event
     * @throws Exception
     */
    public void removeAllOutliers(ActionEvent event) throws Exception {
        double[] mark2 = Outliers.determineEachEntry(Student.studentNumber,
                Student.className);
        clearTable();

        ObservableList<StudentMark> data = tableView3.getItems();

        FileWriter file = new FileWriter(Student.className + "/" + Student
                .studentNumber + ".txt");
        BufferedWriter buffer = new BufferedWriter(file);
        buffer.write(Student.studentFirstName);
        buffer.newLine();
        buffer.write(Student.studentLastName);
        buffer.newLine();
        buffer.write(Student.studentNumber);
        buffer.newLine();

        for (int i = 0; i < Student.marks.size(); i+=4) {
            if (mark2[i/4] * -1 < 0) {
                data.add(new StudentMark(Student.marks.get(i),
                        Student.marks.get(i + 3), Student.marks.get(i + 1),
                        Student.marks.get(i + 2), ""));
                buffer.write(Student.marks.get(i));
                buffer.newLine();
                buffer.write(Student.marks.get(i+1));
                buffer.newLine();
                buffer.write(Student.marks.get(i+2));
                buffer.newLine();
                buffer.write(Student.marks.get(i+3));
                buffer.newLine();
            }
            else {
                Student.marks.remove(i);
                Student.marks.remove(i);
                Student.marks.remove(i);
                Student.marks.remove(i);
            }
        }
        buffer.close();

        Student.reRead();
        Student.average();

        mark.setText(Student.mark);
        IBScore.setText(Student.convert());
        OSSDMark.setText(Student.ossdConvert());
    }

    /**
     * Deletes the selected row and its respective files in the database
     * @param event
     * @throws Exception
     */
    public void removeRow(ActionEvent event) throws Exception {
        boolean found = true;
        StudentMark person = (StudentMark) tableView3.getSelectionModel()
                .getSelectedItem();
        ObservableList<StudentMark> coursesSelected, allCourses;
        allCourses = tableView3.getItems();
        coursesSelected = tableView3.getSelectionModel().getSelectedItems();


        /**
         * FILE I/O
         */
        FileWriter file = new FileWriter(Student.className + "/" + Student
                .studentNumber + ".txt");
        BufferedWriter buffer = new BufferedWriter(file);
        buffer.write(Student.studentFirstName);
        buffer.newLine();
        buffer.write(Student.studentLastName);
        buffer.newLine();
        buffer.write(Student.studentNumber);
        buffer.newLine();

        for (int i = 0; i < Student.marks.size(); i+=4) {
            if (Student.marks.get(i).equals(person.getEvaluationName())
                    && Student.marks.get(i+1).equals(person.getMark())
                    && Student.marks.get(i+2).equals(person.getOutOf())
                    && Student.marks.get(i+3).equals(person.getWeight())
                    && found) {
                found = false;
            }
            else {
                buffer.write(Student.marks.get(i));
                buffer.newLine();
                buffer.write(Student.marks.get(i+1));
                buffer.newLine();
                buffer.write(Student.marks.get(i+2));
                buffer.newLine();
                buffer.write(Student.marks.get(i+3));
                buffer.newLine();
            }
        }
        buffer.close();

        /**
         * REFRESH
         */
        Student.reRead();
        Student.average();

        /**
         * DISPLAY
         */
        mark.setText(Student.mark);
        IBScore.setText(Student.convert());
        OSSDMark.setText(Student.ossdConvert());

        coursesSelected.forEach(allCourses::remove);
    }

    /**
     * Clears the table to ensure that it is empty upon each time it is uponed
     * @throws Exception
     */
    public void clearTable() throws Exception {
        ObservableList<StudentMark> data = tableView3.getItems();
        data.removeAll(data);
    }
}
