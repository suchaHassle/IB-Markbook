/**
 * This class is the controller to handle events that happen in Classroom.fxml
 */

package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.util.InputMismatchException;

public class ClassroomController {
    public static Stage stage;
    @FXML private TableView<Person> tableView;
    @FXML private TableView<Mark> tableView2;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField studentNumber;
    @FXML private Label name;
    @FXML private Label mark;
    @FXML private Button add;
    @FXML private Button delete;
    @FXML private Button conver;
    @FXML private Button viewEvaluations;

    private boolean isIBScore = false;

    /**
     * Deletes the student from the Table View and removes all their files
     * pertaining to them.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    protected void deleteStudent(ActionEvent event) throws Exception {
        if (AlertBox.display()) {
            Person person = tableView.getSelectionModel().getSelectedItem();
            ObservableList<Person> coursesSelected, allCourses;
            allCourses = tableView.getItems();
            coursesSelected = tableView.getSelectionModel().getSelectedItems();

            coursesSelected.forEach(allCourses::remove);
            for (int i = 2; i < Classroom.students.size(); i += 4) {
                if (Classroom.students.get(i).equals(person.getStudentNumber())) {
                    Classroom.students.remove(i - 2); // First Name
                    Classroom.students.remove(i - 2); // Last Name
                    Classroom.students.remove(i - 2); // Student Number
                    Classroom.students.remove(i - 2); // Average
                    break;
                }
            }
            File fileNow = new File(Classroom.className + "/" + person
                    .getStudentNumber() + ".txt");
            fileNow.delete();

            FileWriter file = new FileWriter(Classroom.className + ".IB");
            BufferedWriter buffer = new BufferedWriter(file);

            for (int i = 0; i < 13; i++) {
                buffer.write(Classroom.markBoundaries[i] + "");
                buffer.newLine();
            }
            buffer.write(Classroom.markBoundaries[13] + "");
            for (int i = 0; i < Classroom.students.size(); i++) {
                buffer.newLine();
                buffer.write(Classroom.students.get(i));
            }

            buffer.close();

            ObservableList<Mark> data = tableView2.getItems();
            data.remove(person);
        }
    }

    /**
     * Adds the student and creates all the necessary files to be added to
     * the database
     *
     * @param event
     * @throws Exception
     */
    @FXML
    protected void addStudent(ActionEvent event) throws Exception {
        if (valid()) {
            ObservableList<Person> data = tableView.getItems();

            data.add(new Person(firstName.getText(), lastName.getText(),
                    studentNumber.getText()));

            FileWriter file = new FileWriter(Classroom.className+".IB", true);
            BufferedWriter buffer = new BufferedWriter(file);

            buffer.newLine();
            buffer.write(firstName.getText());
            buffer.newLine();
            buffer.write(lastName.getText());
            buffer.newLine();
            buffer.write(studentNumber.getText());
            buffer.newLine();
            buffer.write("-1");

            buffer.close();
            firstName.setText("");
            lastName.setText("");
            studentNumber.setText("");

            Classroom.students.add(firstName.getText());
            Classroom.students.add(lastName.getText());
            Classroom.students.add(studentNumber.getText());
            Classroom.students.add("-1");

            Classroom.readData();
        }
        else
            displayError();
    }

    /**
     * Displays error message when the user's input is incorrect
     */
    public static void displayError() {
        Stage errorPrompt = new Stage ();

        errorPrompt.initModality(Modality.APPLICATION_MODAL);
        errorPrompt.setTitle ("IB Markbook");
        errorPrompt.setMinWidth(250);

        Label label = new Label();
        label.setText("Your input is incorrect!! Please try again.");
        Button OK = new Button("OK");
        OK.setOnAction(f -> {
            try {
                errorPrompt.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20,20,20,20));
        layout.setVgap(8);
        layout.setHgap(10);
        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(OK, 1,1);
        layout.getChildren().addAll(label, OK);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        errorPrompt.setScene(scene);
        errorPrompt.showAndWait();
    }

    /**
     * When a student is selected, it needs to populate the table on the
     * right as well as the two labels with Name and Mark
     *
     * @param event
     * @throws Exception
     */
    public void selectedStudent(MouseEvent event) throws Exception {
        Person person = tableView.getSelectionModel().getSelectedItem();

        name.setText(person.getFirstName() + " " + person.getLastName());
        mark.setText(person.getMarkDecimal());

        for (int i = 2; i < Classroom.students.size(); i+=4) {
            if (person.getStudentNumber().equals(Classroom.students.get(i)
                    )) {
                FileReader file = new FileReader(Classroom.className
                        + "/" + person.getStudentNumber() + ".txt");
                BufferedReader buffer = new BufferedReader (file);

                buffer.readLine();
                buffer.readLine();
                buffer.readLine();

                String currentLine = buffer.readLine();
                ObservableList<Mark> data = tableView2.getItems();
                data.removeAll(data);

                while (currentLine != null) {
                    String evalName = currentLine;
                    String studentMark = buffer.readLine();
                    String markDenom = buffer.readLine();
                    buffer.readLine();
                    data.add(new Mark(evalName, studentMark+"/"+markDenom));
                    currentLine = buffer.readLine();
                }
                break;
            }
        }
    }

    /**
     * Determines whether the newly added student has valid input
     *
     * @return
     * @throws Exception
     */
    public boolean valid () throws Exception {
        if (firstName.getText().equals ("") || firstName.getText()
                .startsWith(" "))
            return false;
        else if (lastName.getText().equals ("") || lastName.getText()
                .startsWith(" "))
            return false;
        try {
            Integer.parseInt(studentNumber.getText());

            for (int i = 2; i < Classroom.students.size(); i+=4)
                if (Classroom.students.get(i).equals(Integer.parseInt
                        (studentNumber.getText())+""))
                    return false;
        } catch (NumberFormatException e) {
            return false;
        } catch (InputMismatchException e) {
            return false;
        }
        return true;
    }

    /**
     * This is a button event handler that when pressed launches the new
     * evaluation fxml file and closes the current window
     *
     * @param event
     * @throws Exception
     */
    public void newEvaluation(ActionEvent event) throws Exception {
        if (studentExists()) {
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = (Parent) loader.load(Classroom.class
                    .getResource("newEval.fxml").openStream());
            stage = new Stage();
            stage.setOnCloseRequest(e -> {
                try {
                    Classroom.menu();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            newEvalController controller = loader.getController();
            controller.autoFillComboBox();

            stage.setTitle("IB Markbook");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            displayNoStudentError();
        }
    }

    /**
     * If the user attempts to add a new evaluation, it checks to see if
     * there are students in the classroom, if not, it alerts the user there
     * are no students in the classroom
     */
    public void displayNoStudentError() {
        Stage errorPrompt = new Stage ();

        errorPrompt.initModality(Modality.APPLICATION_MODAL);
        errorPrompt.setTitle ("IB Markbook");
        errorPrompt.setMinWidth(250);

        Label label = new Label();
        label.setText("You do not have any students! Please add one to " +
                "continue.");
        Button OK = new Button("OK");
        OK.setOnAction(f -> {
            try {
                errorPrompt.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20,20,20,20));
        layout.setVgap(8);
        layout.setHgap(10);
        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(OK, 1,1);
        layout.getChildren().addAll(label, OK);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        errorPrompt.setScene(scene);
        errorPrompt.showAndWait();
    }

    /**
     * If the user attempts to add a new evaluation, it checks to see if
     * there are students in the classroom
     *
     * @return whether there are any students in the classroom
     */
    public boolean studentExists() {
        return (!Classroom.students.isEmpty());
    }

    /**
     * This method is a button event handler that opens the grade boundaries
     * to be modified.
     *
     * @param event
     * @throws Exception
     */
    public void gradeBoundaries(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(Classroom.class
                .getResource("boundaries.fxml").openStream());
        stage = new Stage();
        stage.setOnCloseRequest(e -> {
            try {
                Classroom.menu();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        boundariesController controller = loader.getController();
        controller.updateIBBoundaries();

        stage.setTitle("IB Markbook");
        stage.setScene(new Scene(root));
        stage.show();
        Classroom.stage.close();
    }

    /**
     * This method is a button event handler, it opens the individual
     * student fxml file for a teacher to further look at whether a student
     * may have outliers or delete entries in the student's record
     *
     * @throws Exception
     */
    public void openStudent() throws Exception {
        Person person = tableView.getSelectionModel().getSelectedItem();

        if (person != null) {
            Classroom.stage.close();
            String studentNumber = person.getStudentNumber();
            String className = Classroom.className;
            String mark = person.getMarkDecimal();

            Student student = new Student(studentNumber, className, mark);
            Student.display();
        }
    }

    /**
     * This populates the table with the student's IB score
     *
     * @throws Exception
     */
    public void convert() throws Exception {
        if (isIBScore) {
            populateTable();
            conver.setText("Convert to IB");
            isIBScore = false;
        }
        else if (!isIBScore && Classroom.markBoundaries[0] != -1) {
            ObservableList<Person> data = tableView.getItems();
            data.removeAll(data);
            for (int i = 0; i < Classroom.students.size(); i += 4) {
                if (Classroom.students.get(i + 3).equals("-1")) {
                    data.add(new Person(Classroom.students.get(i),
                            Classroom.students.get(i + 1),
                            Classroom.students.get(i + 2)));
                } else {
                    Convert con = new Convert(Classroom.markBoundaries,
                            Classroom.ontarioMarks);

                    String outlier = "";

                    if (Outliers.determineStudent(Classroom.students.get(i+2),
                            Classroom.className))
                        outlier = "*";

                    data.add(new Person(Classroom.students.get(i),
                            Classroom.students.get(i + 1),
                            Classroom.students.get(i + 2),
                            con.IBScore(Double.parseDouble(Classroom
                                    .students.get(i + 3))), outlier));
                }
            }
            isIBScore = true;
            conver.setText("Convert back to raw");
        }
    }

    /**
     * This is a button event handler that creates the PDF with all the
     * student marks to be displayed.
     *
     * @throws Exception
     */
    public void print() throws Exception {
        final int NUMBER_OF_COLUMNS = 4;

        if (Classroom.markBoundaries[0] != -1) {
            Document document = new Document();
            try {
                Font standardFont = new Font(Font.FontFamily.COURIER, 12);
                Font titleFont = new Font(Font.FontFamily.COURIER, 18);

                PdfWriter.getInstance(document, new FileOutputStream(Classroom
                        .className + ".pdf"));

                document.open();

                document.add(new Paragraph(Classroom.className,
                        titleFont));
                document.add(Chunk.NEWLINE);

                PdfPTable table = new PdfPTable(NUMBER_OF_COLUMNS);

                PdfPCell studentNumberLabel = new PdfPCell(new Paragraph
                        ("\t\tStudent Number", standardFont));
                PdfPCell rawMarkLabel = new PdfPCell(new Paragraph("\t\tMark",
                        standardFont));
                PdfPCell scoreLabel = new PdfPCell(new Paragraph("\t\tIB Score"
                        ,standardFont));
                PdfPCell convertedLabel = new PdfPCell(new Paragraph
                        ("\t\tOSSD", standardFont));

                table.addCell(studentNumberLabel);
                table.addCell(rawMarkLabel);
                table.addCell(scoreLabel);
                table.addCell(convertedLabel);

                Convert con = new Convert(Classroom.markBoundaries,
                        Classroom.ontarioMarks);

                for (int i = 0; i < Classroom.students.size(); i += 4) {
                    PdfPCell studentNumber = new PdfPCell(new Paragraph
                            (Classroom.students.get(i + 2), standardFont));
                    PdfPCell mark = new PdfPCell(new Paragraph(Classroom
                            .students.get(i + 3), standardFont));
                    PdfPCell IBScore = new PdfPCell(new Paragraph("No " +
                            "Mark", standardFont));
                    PdfPCell ossd = new PdfPCell(new Paragraph("No Mark",
                            standardFont));

                    if (!Classroom.students.get(i+3).equals("No Mark") &&
                            !Classroom.students.get(i+3).equals("-1")) {
                        IBScore = new PdfPCell(new Paragraph(con.IBScore
                                (Double.parseDouble(Classroom
                                        .students.get(i + 3))), standardFont));
                        ossd = new PdfPCell(new Paragraph(con.convert(
                                Double.parseDouble(Classroom.students.get(i + 3)
                                )) + "", standardFont));
                    }

                    table.addCell(studentNumber);
                    table.addCell(mark);
                    table.addCell(IBScore);
                    table.addCell(ossd);
                }

                document.add(table);

                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);

                for (int i = 0; i < 14; i+=2) {
                    document.add(new Paragraph("\t\tLevel " + ((i/2) + 1) +
                            ": " + Classroom.markBoundaries[i] + " to "
                            + Classroom.markBoundaries[i+1], standardFont));
                }

                document.close();

                Stage errorPrompt = new Stage ();

                errorPrompt.initModality(Modality.APPLICATION_MODAL);
                errorPrompt.setTitle ("IB Markbook");
                errorPrompt.setMinWidth(250);

                Label label = new Label();
                label.setText("Document has successfully been created!\n" +
                        "Open " + Classroom.className + ".pdf, Click File - " +
                        "Print");
                Button OK = new Button("OK");
                OK.setOnAction(f -> {
                    try {
                        errorPrompt.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });

                GridPane layout = new GridPane();
                layout.setPadding(new Insets(20,20,20,20));
                layout.setVgap(8);
                layout.setHgap(10);
                GridPane.setConstraints(label, 0, 0);
                GridPane.setConstraints(OK, 0,1);
                layout.getChildren().addAll(label, OK);
                layout.setAlignment(Pos.CENTER);

                Scene scene = new Scene(layout);
                errorPrompt.setScene(scene);
                errorPrompt.showAndWait();

            } catch (DocumentException e) {

            }
        }
        // If the teacher has not set up IB grade boundaries, it will output
        // this to tell the teacher to create boundaries.
        else {
            Stage errorPrompt = new Stage ();

            errorPrompt.initModality(Modality.APPLICATION_MODAL);
            errorPrompt.setTitle ("IB Markbook");
            errorPrompt.setMinWidth(250);

            Label label = new Label();
            label.setText("You have not set up IB grade boundaries. Please " +
                    "do so to print");
            Button OK = new Button("OK");
            OK.setOnAction(f -> {
                try {
                    errorPrompt.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

            GridPane layout = new GridPane();
            layout.setPadding(new Insets(20,20,20,20));
            layout.setVgap(8);
            layout.setHgap(10);
            GridPane.setConstraints(label, 0, 0);
            GridPane.setConstraints(OK, 1,1);
            layout.getChildren().addAll(label, OK);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            errorPrompt.setScene(scene);
            errorPrompt.showAndWait();
        }
    }

    /**
     * Populates the table with all the students before the FXML is displayed.
     *
     * @throws Exception
     */
    public void populateTable() throws Exception {
        isIBScore = false;
        ObservableList<Person> data = tableView.getItems();
        data.removeAll(data);
        Classroom.average();
        for (int i = 0; i < Classroom.students.size(); i+=4) {
            if (Classroom.students.get(i+3).equals("-1"))
                data.add(new Person(Classroom.students.get(i),
                        Classroom.students.get(i+1),
                        Classroom.students.get(i+2)));
            else {
                String outlier = "";

                if (Outliers.determineStudent(Classroom.students.get(i+2),
                        Classroom.className))
                    outlier = "*";

                data.add(new Person(Classroom.students.get(i),
                        Classroom.students.get(i + 1),
                        Classroom.students.get(i + 2),
                        Classroom.students.get(i + 3),
                        outlier));
            }
        }
    }
}
