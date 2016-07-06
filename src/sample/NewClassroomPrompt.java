package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewClassroomPrompt extends Main {
    /**
     * This method displays the interface to allow users to create new
     * classrooms and creates their respective files.
     *
     * @throws Exception
     */
    public static void display () throws Exception {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle ("IB Markbook");
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
                if (classesID.isEmpty())
                    System.exit(0);
                else
                    stage.close();
        });

        Label title = new Label();
        title.setText("Create new classroom");
        Label name = new Label();
        name.setText("Name: ");
        Button cancelButton = new Button ("Cancel");
        cancelButton.setOnAction (e -> System.exit(0));
        TextField className = new TextField();
        Button okButton = new Button("OK");
        okButton.setOnAction(e-> {
            try {
                if (!classroomExists(className.getText())) {
                    stage.close();
                    FileWriter file = null;
                    file = new FileWriter("class.IB", true);
                    BufferedWriter buffer = new BufferedWriter(file);
                    buffer.newLine();
                    buffer.write(className.getText());
                    buffer.close();
                    FileWriter newFile = new FileWriter (className.getText()
                            + ".IB");
                    BufferedWriter buff = new BufferedWriter(newFile);

                    for (int j = 0; j < 13; j++) {
                        buff.write("-1");
                        buff.newLine();
                    }
                    buff.write("-1");

                    buff.close();
                    classesID.add(className.getText());
                    startupController.displayDashboard();
                }
                else {
                    Stage errorPrompt = new Stage ();

                    errorPrompt.initModality(Modality.APPLICATION_MODAL);
                    errorPrompt.setTitle ("IB Markbook");
                    errorPrompt.setMinWidth(250);

                    Label label = new Label();
                    label.setText("That classroom already exists!");
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
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
        });
        HBox hbox = new HBox();
        hbox.setSpacing(10.0);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(30,30,30,30));
        layout.setVgap(8);
        layout.setHgap(10);
        hbox.getChildren().addAll(okButton,cancelButton);
        GridPane.setConstraints(title, 1, 0);
        GridPane.setConstraints(name, 1, 2);
        GridPane.setConstraints(className, 2, 2);
        GridPane.setConstraints(hbox, 2, 6);

        layout.getChildren().addAll(title, name, className, hbox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Determines whether the classroom that the user made already exists
     *
     * @param course
     * @return returns the course whether the classroom exists
     */
    public static boolean classroomExists(String course) {
        for (int i = 0; i < classesID.size(); i++) {
            if (classesID.get(i).equalsIgnoreCase(course))
                return true;
        }
        return false;
    }
}
