/**
 * This is a contoller for ontario.fxml
 */
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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

public class OSSDController extends Main {

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

    /**
     * The following methods are listeners that change the upcoming or the
     * textfield before. Ensure that the intervals from each textfield level
     * is one away from another
     *
     * @param event
     * @throws Exception
     */
    public void level1Listner(KeyEvent event) throws Exception {
        level2low.setText((Integer.parseInt(level1up.getText()) + 1) + "");
    }
    public void level2lowListner(KeyEvent event) throws Exception {
        level1up.setText((Integer.parseInt(level2low.getText()) - 1) + "");
    }
    public void level2upListner(KeyEvent event) throws Exception {
        level3low.setText((Integer.parseInt(level2up.getText()) + 1) + "");
    }
    public void level3lowListner(KeyEvent event) throws Exception {
        level2up.setText((Integer.parseInt(level3low.getText()) - 1) + "");
    }
    public void level3upListner(KeyEvent event) throws Exception {
        level4low.setText((Integer.parseInt(level3up.getText()) + 1) + "");
    }
    public void level4lowListner(KeyEvent event) throws Exception {
        level3up.setText((Integer.parseInt(level4low.getText()) - 1) + "");
    }
    public void level4upListner(KeyEvent event) throws Exception {
        level5low.setText((Integer.parseInt(level4up.getText()) + 1) + "");
    }
    public void level5lowListner(KeyEvent event) throws Exception {
        level4up.setText((Integer.parseInt(level5low.getText()) - 1) + "");
    }
    public void level5upListner(KeyEvent event) throws Exception {
        level6low.setText((Integer.parseInt(level5up.getText()) + 1) + "");
    }
    public void level6lowListner(KeyEvent event) throws Exception {
        level5up.setText((Integer.parseInt(level6low.getText()) - 1) + "");
    }
    public void level6upListner(KeyEvent event) throws Exception {
        level7low.setText((Integer.parseInt(level6up.getText()) + 1) + "");
    }
    public void level7lowListner(KeyEvent event) throws Exception {
        level6up.setText((Integer.parseInt(level7low.getText()) - 1) + "");
    }


    /**
     * Since the user no longer wants to proceed to finishing creating their
     * account, the program exits instead.
     *
     * @param event - Required call for actions in FXML file
     * @throws Exception
     */
    public void close (ActionEvent event) throws Exception {
        if (!startupController.controllAccessor) {
            window.close();
            File file = new File(CLASS_FILE);
            file.delete();
            System.exit(0);
        }
        else {
            startupController.stage.close();
            startupController.controllAccessor = true;
        }
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
            ontarioMarks[0] = 0;
            ontarioMarks[1] = Integer.parseInt(level1up.getText());
            ontarioMarks[3] = Integer.parseInt(level2up.getText());
            ontarioMarks[5] = Integer.parseInt(level3up.getText());
            ontarioMarks[7] = Integer.parseInt(level4up.getText());
            ontarioMarks[9] = Integer.parseInt(level5up.getText());
            ontarioMarks[11] = Integer.parseInt(level6up.getText());
            ontarioMarks[2] = Integer.parseInt(level2low.getText());
            ontarioMarks[4] = Integer.parseInt(level3low.getText());
            ontarioMarks[6] = Integer.parseInt(level4low.getText());
            ontarioMarks[8] = Integer.parseInt(level5low.getText());
            ontarioMarks[10] = Integer.parseInt(level6low.getText());
            ontarioMarks[12] = Integer.parseInt(level7low.getText());
            ontarioMarks[13] = 100;

            FileWriter file = new FileWriter (CLASS_FILE);
            BufferedWriter buffer = new BufferedWriter (file);

            buffer.write(username);
            buffer.newLine();
            buffer.write(password);
            buffer.newLine();
            for (int i = 0; i < 13; i++) {
                System.out.print (ontarioMarks[i]);
                buffer.write(ontarioMarks[i] + "");
                buffer.newLine();
            }
            buffer.write(ontarioMarks[13] + ""); // Prevents writing a new line
            for (int i = 0; i < classesID.size(); i++) {
                buffer.newLine();
                buffer.write(classesID.get(i));
            }
            buffer.close();
            window.close();
            startupController.controllAccessor = false;
            startupController.stage.close();
        }
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
            if (Integer.parseInt(level1up.getText()) <= 0)
                return false;
            if (Integer.parseInt(level2up.getText()) <= Integer.parseInt
                    (level2low.getText()))
                return false;
            if (Integer.parseInt(level3up.getText()) <= Integer.parseInt
                    (level3low.getText()))
                return false;
            if (Integer.parseInt(level4up.getText()) <= Integer.parseInt
                    (level4low.getText()))
                return false;
            if (Integer.parseInt(level5up.getText()) <= Integer.parseInt
                    (level5low.getText()))
                return false;
            if (Integer.parseInt(level6up.getText()) <= Integer.parseInt
                    (level6low.getText()))
                return false;
            if (Integer.parseInt(level7low.getText()) >= 100)
                return false;

            return true;
        } catch (NumberFormatException e1) {
            return false;
        }
    }
    /**
     * Updates all the Ontario marks in Classroom class in Main.java
     *
     * @throws Exception
     */
    public void updateOntarioMarks () throws Exception {
        level1up.setText(ontarioMarks[1] + "");
        level2up.setText(ontarioMarks[3] + "");
        level3up.setText(ontarioMarks[5] + "");
        level4up.setText(ontarioMarks[7] + "");
        level5up.setText(ontarioMarks[9] + "");
        level6up.setText(ontarioMarks[11] + "");
        level2low.setText(ontarioMarks[2] + "");
        level3low.setText(ontarioMarks[4] + "");
        level4low.setText(ontarioMarks[6] + "");
        level5low.setText(ontarioMarks[8] + "");
        level6low.setText(ontarioMarks[10] + "");
        level7low.setText(ontarioMarks[12] + "");
    }
}
