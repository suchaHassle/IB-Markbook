/**
 * This is the controller for newEval.fxml that allows users to create new
 * evaluations to be added in each student's mark
 */
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.InputMismatchException;

public class newEvalController {

    @FXML private TextField denominator;
    @FXML private TextField weighting;
    @FXML private TextField customText;
    @FXML private ComboBox<String> comboBox;
    @FXML private RadioButton test;
    @FXML private RadioButton quiz;
    @FXML private RadioButton assign;
    @FXML private RadioButton custom;
    @FXML private Button okButton;
    @FXML private Button cancelButton;

    /**
     * When the combobox receives a click that of all the options available,
     * it populate all the other fields
     *
     * @param event
     * @throws Exception
     */
    public void autoSelect (ActionEvent event) throws Exception {
        if (comboBox.getValue().equals("Internal Assessment")) {
            weighting.setText("30");
            customText.setText("IB");
            custom.setSelected(true);
        }
        if (comboBox.getValue().equals("Midterm Exam")) {
            denominator.setText("100");
            weighting.setText("30");
            customText.setText("IB");
            custom.setSelected(true);
        }
        if (comboBox.getValue().equals("Mock Exam")) {
            denominator.setText("100");
            weighting.setText("10");
            customText.setText("Other");
            custom.setSelected(true);
        }
    }

    /**
     * When the textfield for type of evaluation is clicked on, the
     * corresponding radio button is then activated
     *
     * @param event
     * @throws Exception
     */
    public void customSelect (MouseEvent event) throws Exception {
        custom.setSelected(true);
    }

    /**
     * This method is for populating the combo box with the different IB
     * assessments that are needed.
     *
     * @throws Exception
     */
    public void autoFillComboBox () throws Exception {
        comboBox.getItems().addAll ("Internal Assessment", "Midterm Exam",
                "Mock Exam");
        Classroom.stage.close();
    }

    /**
     * This is a button event handler that closes the window and launches
     * the classroom dashboard
     *
     * @throws Exception
     */
    public void cancel() throws Exception {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Classroom.menu();
    }

    /**
     * This is a button event handler that closes the window and launches
     * New Evaluation Entries to allow the teacher input the marks of all
     * students.
     *
     * @throws Exception
     */
    public void OK () throws Exception {
        System.out.println(comboBox.getEditor().getText());

        if (valid()) {
            String type;

            if (test.isSelected())
                type = "Test";
            else if (quiz.isSelected())
                type = "Quiz";
            else if (assign.isSelected())
                type = "Assignment";
            else
                type = customText.getText();

            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
            NewEvaluationEntries.display(denominator.getText(), comboBox
                    .getEditor().getText(), weighting.getText(), type);
            Classroom.menu();
        }
        else {
            displayError();
        }
    }

    /**
     * Determines whether the textfields and radio buttons are correct
     *
     * @return whether the input is valid
     * @throws Exception
     */
    public boolean valid() throws Exception {
        try {
            if (Integer.parseInt(weighting.getText()) < 0 || Integer
                    .parseInt(weighting.getText()) > 100)
                return false;
            if (Integer.parseInt(denominator.getText()) <= 0)
                return false;
            if (!test.isSelected() && !quiz.isSelected() && !assign
                    .isSelected() && !custom.isSelected())
                return false;
            if (comboBox.getEditor().getText() == null ||
                    comboBox.getEditor().getText().equals(""))
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        catch (InputMismatchException e) {
            return false;
        }
        return true;
    }

    /**
     * Display an error message saying that the users input for the
     * textfields and radio buttons are incorrect
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
}
