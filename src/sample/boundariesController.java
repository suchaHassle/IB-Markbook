/**
 * This class is the controller for the boundaries.fxml file, which allows
 * teachers to modify the IB boundaries that correspond to each classroom
 */
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class boundariesController {

    @FXML private TextField level1up;
    @FXML private TextField level2low;
    @FXML private TextField level2up;
    @FXML private TextField level3low;
    @FXML private TextField level3up;
    @FXML private TextField level4low;
    @FXML private TextField level4up;
    @FXML private TextField level5low;
    @FXML private TextField level5up;
    @FXML private TextField level6low;
    @FXML private TextField level6up;
    @FXML private TextField level7low;
    @FXML private Button OKButton;
    @FXML private Button cancelButton;

    /**
     * The following methods are listeners that change the upcoming or the
     * textfield before. Ensure that the intervals from each textfield level
     * is one away from another
     *
     * @param event
     * @throws Exception
     */
    public void level1Listner(KeyEvent event) throws Exception {
        level2low.setText((Double.parseDouble(level1up.getText()) + 1) + "");
    }
    public void level2lowListner(KeyEvent event) throws Exception {
        level1up.setText((Double.parseDouble(level2low.getText()) - 1) + "");
    }
    public void level2upListner(KeyEvent event) throws Exception {
        level3low.setText((Double.parseDouble(level2up.getText()) + 1) + "");
    }
    public void level3lowListner(KeyEvent event) throws Exception {
        level2up.setText((Double.parseDouble(level3low.getText()) - 1) + "");
    }
    public void level3upListner(KeyEvent event) throws Exception {
        level4low.setText((Double.parseDouble(level3up.getText()) + 1) + "");
    }
    public void level4lowListner(KeyEvent event) throws Exception {
        level3up.setText((Double.parseDouble(level4low.getText()) - 1) + "");
    }
    public void level4upListner(KeyEvent event) throws Exception {
        level5low.setText((Double.parseDouble(level4up.getText()) + 1) + "");
    }
    public void level5lowListner(KeyEvent event) throws Exception {
        level4up.setText((Double.parseDouble(level5low.getText()) - 1) + "");
    }
    public void level5upListner(KeyEvent event) throws Exception {
        level6low.setText((Double.parseDouble(level5up.getText()) + 1) + "");
    }
    public void level6lowListner(KeyEvent event) throws Exception {
        level5up.setText((Double.parseDouble(level6low.getText()) - 1) + "");
    }
    public void level6upListner(KeyEvent event) throws Exception {
        level7low.setText((Double.parseDouble(level6up.getText()) + 1) + "");
    }
    public void level7lowListner(KeyEvent event) throws Exception {
        level6up.setText((Double.parseDouble(level7low.getText()) - 1) + "");
    }


    /**
     * Since the user no longer wants to proceed to finishing creating their
     * account, the program exits instead.
     *
     * @param event - Required call for actions in FXML file
     * @throws Exception
     */
    public void close (ActionEvent event) throws Exception {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Classroom.menu();
    }

    /**
     * After completing the information, it proceeds to the login screen for
     * the user to re-log back in.
     *
     * @param event - Required call for actions in FXML file
     * @throws Exception
     */
    public void OK (ActionEvent event) throws Exception {
        if (isValid()) {
            Classroom.markBoundaries[0] = 0;
            Classroom.markBoundaries[1] = Double.parseDouble(level1up
                    .getText());
            Classroom.markBoundaries[3] = Double.parseDouble(level2up
                    .getText());
            Classroom.markBoundaries[5] = Double.parseDouble(level3up
                    .getText());
            Classroom.markBoundaries[7] = Double.parseDouble(level4up
                    .getText());
            Classroom.markBoundaries[9] = Double.parseDouble(level5up
                    .getText());
            Classroom.markBoundaries[11] = Double.parseDouble(level6up
                    .getText());
            Classroom.markBoundaries[2] = Double.parseDouble(level2low
                    .getText());
            Classroom.markBoundaries[4] = Double.parseDouble(level3low
                    .getText());
            Classroom.markBoundaries[6] = Double.parseDouble(level4low
                    .getText());
            Classroom.markBoundaries[8] = Double.parseDouble(level5low
                    .getText());
            Classroom.markBoundaries[10] = Double.parseDouble(level6low
                    .getText());
            Classroom.markBoundaries[12] = Double.parseDouble(level7low
                    .getText());
            Classroom.markBoundaries[13] = 100;

            FileWriter file = new FileWriter (Classroom.className + ".IB");
            BufferedWriter buffer = new BufferedWriter (file);

            for (int i = 0; i < 13; i++) {
                buffer.write(Classroom.markBoundaries[i] + "");
                buffer.newLine();
            }
            buffer.write(Classroom.markBoundaries[13] + ""); // Prevents writing a new line
            for (int i = 0; i < Classroom.students.size(); i++) {
                buffer.newLine();
                buffer.write(Classroom.students.get(i));
            }
            buffer.close();
            Stage stage = (Stage) OKButton.getScene().getWindow();
            stage.close();
            Classroom.menu();
        }
        // If the input is invalid, this prompt shows up alerting the user
        else {
            Stage window = new Stage ();

            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle ("IB Markbook");
            window.setMinWidth(250);

            Label label = new Label();
            label.setText("The input is invalid!! Please check again.");
            Button okButton = new Button("OK");
            okButton.setOnAction(e -> {
                try {
                    window.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

            GridPane layout = new GridPane();
            layout.setPadding(new Insets(20,20,20,20));
            layout.setVgap(8);
            layout.setHgap(10);
            GridPane.setConstraints(label, 0, 0);
            GridPane.setConstraints(okButton, 1,1);
            layout.getChildren().addAll(label, okButton);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        }
    }

    /**
     * Checks to see if all the fields are valid and returns whether it is
     * or not
     *
     * @return whether the input is valid or not
     */
    public boolean isValid() {
        try {
            if (Double.parseDouble(level1up.getText()) <= 0)
                return false;
            if (Double.parseDouble(level2up.getText()) <= Double.parseDouble
                    (level2low.getText()))
                return false;
            if (Double.parseDouble(level3up.getText()) <= Double.parseDouble
                    (level3low.getText()))
                return false;
            if (Double.parseDouble(level4up.getText()) <= Double.parseDouble
                    (level4low.getText()))
                return false;
            if (Double.parseDouble(level5up.getText()) <= Double.parseDouble
                    (level5low.getText()))
                return false;
            if (Double.parseDouble(level6up.getText()) <= Double.parseDouble
                    (level6low.getText()))
                return false;
            if (Double.parseDouble(level7low.getText()) >= 100)
                return false;

            return true;
        } catch (NumberFormatException e1) {
            return false;
        }
    }

    /**
     * Updates all the IB boundaries in Classroom class
     *
     * @throws Exception
     */
    public void updateIBBoundaries () throws Exception {
        if (Classroom.markBoundaries[0] != -1) {
            level1up.setText(Classroom.markBoundaries[1] + "");
            level2up.setText(Classroom.markBoundaries[3] + "");
            level3up.setText(Classroom.markBoundaries[5] + "");
            level4up.setText(Classroom.markBoundaries[7] + "");
            level5up.setText(Classroom.markBoundaries[9] + "");
            level6up.setText(Classroom.markBoundaries[11] + "");
            level2low.setText(Classroom.markBoundaries[2] + "");
            level3low.setText(Classroom.markBoundaries[4] + "");
            level4low.setText(Classroom.markBoundaries[6] + "");
            level5low.setText(Classroom.markBoundaries[8] + "");
            level6low.setText(Classroom.markBoundaries[10] + "");
            level7low.setText(Classroom.markBoundaries[12] + "");
        }
    }
}
