/**
 * This is the controller for login and displays all the classrooms
 */
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class startupController extends Main{
    public static String user;
    public static String pass;
    public static Stage dashboard;
    public static Stage stage;

    @FXML private TextField userfield;
    @FXML private TextField passfield;

    public static boolean controllAccessor = false;

    /**
     * When the user clicks quit, it terminates the program
     */
    public void quit () {
        System.exit(0);
    }

    /**
     * Checks to see if the login is correct and returns a prompt if it isn't
     * @param event
     * @throws Exception
     */
    public void checkLogin (ActionEvent event) throws Exception {
        boolean valid = true;
        user = userfield.getText();
        pass = passfield.getText();

        if (!user.equalsIgnoreCase(username) || !pass.equals(password)) {
            valid = false;
        }

        if (!valid) {
            Stage window = new Stage();

            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Error");
            window.setResizable(false);

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(20, 20, 20, 20));
            grid.setVgap(10);
            grid.setHgap(10);

            Label label = new Label();
            label.setText("The username and/or password is incorrect, please" +
                    " try again.");
            grid.add(label, 1, 1);
            Button button = new Button("Close");

            button.setOnAction(e -> window.close());

            grid.add(button, 1, 2);

            Scene scene = new Scene(grid, 435, 120);
            window.setScene(scene);
            window.showAndWait();
        }
        else {
            window.close();
            if (classesID.isEmpty()) {
                Stage error = new Stage ();

                error.setTitle ("IB Markbook");
                error.setMinWidth(250);

                Label label = new Label();
                label.setText("You do not have a classes! Click OK to create" +
                        " one");
                Button okButton = new Button("OK");
                okButton.setOnAction(e -> {
                    try {
                        e.consume();
                        error.close();
                        NewClassroomPrompt.display();
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
                error.setScene(scene);
                error.show();
            }
            else
                displayDashboard();
        }
    }

    /**
     * Displays the dashboard off all the classrooms
     */
    public static void displayDashboard() {
        dashboard = new Stage();
        dashboard.initModality(Modality.APPLICATION_MODAL);
        dashboard.setTitle ("IB Markbook");
        dashboard.setMinWidth(250);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getMenuBar());
        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();
        GridPane grid3 = new GridPane();
        grid.setPadding(new Insets(0, 20, 20, 20));
        grid2.setPadding(new Insets (50, 20, 20, 20));
        grid3.setPadding(new Insets (20, 20, 20, 20));
        HBox hButtons = new HBox();
        borderPane.setCenter(grid);
        hButtons.setSpacing(25.0);

        Label labelWelcomeBack = new Label("Select a Classroom");
        labelWelcomeBack.setStyle("-fx-font-size: 20;");
        GridPane.setConstraints(labelWelcomeBack, 0, 0);

        for (int i = 0; i < classesID.size(); i++) {
            Button classes = new Button (classesID.get(i));
            classes.setOnAction(e -> {
                Classroom classroom = new Classroom (ontarioMarks, classes
                        .getText());
                dashboard.close();
                try {
                    Classroom.menu();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            hButtons.getChildren().add(classes);
        }
        GridPane.setConstraints(grid2, 0, 1);
        grid2.getChildren().add(hButtons);
        grid3.getChildren().add(labelWelcomeBack);
        grid.getChildren().addAll(grid3, grid2);
        grid.setAlignment(Pos.CENTER);
        grid2.setAlignment(Pos.CENTER);
        grid3.setAlignment(Pos.CENTER);
        Scene scene = new Scene(borderPane, 500, 400);
        dashboard.setScene(scene);
        dashboard.show();
    }

    /**
     * Creates the menu bar
     *
     * @return menu bar for the dashboard
     */
    public static MenuBar getMenuBar ()
    {
        MenuBar menuBar = new MenuBar();

        Menu menuEdit = new Menu ("Edit");
        MenuItem menuCreateNewClassroom = new MenuItem ("Create New " +
                "Classroom");
        MenuItem menuDeleteClassroom = new MenuItem("Delete a Classroom");
        MenuItem menuEditOntarioGrades = new MenuItem("Edit Ontario Grade " +
                "Boundaries");
        MenuItem menuClose = new MenuItem("Close");
        menuEdit.getItems().addAll (menuCreateNewClassroom,
                menuDeleteClassroom, menuEditOntarioGrades, menuClose);

        menuCreateNewClassroom.setOnAction(e -> {
            try {
                dashboard.close();
                NewClassroomPrompt.display();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        menuDeleteClassroom.setOnAction(e -> {
            try {
                manageClassrooms.display();
                dashboard.close();
                displayDashboard();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        menuEditOntarioGrades.setOnAction(e -> {
            dashboard.close();
            try {
                controllAccessor = true;
                FXMLLoader loader = new FXMLLoader();
                Parent root = (Parent) loader.load(startupController.class
                        .getResource("ontario.fxml").openStream());
                stage = new Stage();

                OSSDController controller = loader.getController();
                controller.updateOntarioMarks();

                stage.setTitle("IB Markbook");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (!controllAccessor)
                    OSSDConfirmation.display("Successfully changed Ontario " +
                        "grade boundaries!");
            }
            catch (IOException err) {
                System.out.println("Error");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            displayDashboard();
        });
        menuClose.setOnAction(e -> System.exit(0));

        menuBar.getMenus().addAll(menuEdit);

        return menuBar;
    }
}
