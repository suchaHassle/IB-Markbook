package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller extends Main
{
    public static Stage grades;
    public static Stage classroom;
    public static Stage previousStage;

    /**
     * Closes the IB Grade boundaries prompt, this method is the controller
     * for the cancel button found on IB Grade Boundaries.
     *
     * @param event - Required call for actions in FXML file
     * @throws Exception
     */
    public void closeGrades(ActionEvent event) throws Exception {
        grades.close();
    }
    public void classroom(ActionEvent event) throws Exception {
        window.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "classroom.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        classroom = new Stage();
        classroom.setScene(new Scene(root1));
        classroom.show();
        previousStage = classroom;
    }

    /**
     * This method saves the new user data and proceeds back the classroom
     *
     * @param event - Required call for actions in FXML file
     * @throws Exception
     */
    public void OK (ActionEvent event) throws Exception {
        grades.hide();
        classroom.show();
    }

    /**
     * Display the main menu/hub for the user that display student
     * information and their raw averages
     *
     * @param event - Required call for actions in FXML file
     * @throws Exception
     */
    public void grades(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "boundaries.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        grades = new Stage();
        grades.setScene(new Scene(root1));
        grades.show();
    }
    public static void renameClassroom () {

    }
    public static void deleteClassroom () {

    }
}