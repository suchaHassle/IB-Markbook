package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;

public class NewEvaluationEntries {
    static CheckBox[] noMark = new CheckBox[Classroom.students.size() / 4];
    static TextField[] mark = new TextField[Classroom.students.size() / 4];

    static Stage stage = new Stage();

    static String denomor;
    static String nameOfEval;
    static String weight;

    public static void display(String denominator, String eval, String
            weighting, String type) throws Exception {
        denomor = denominator;
        nameOfEval = type + " - " + eval;
        weight = weighting;

        stage.setTitle ("IB Markbook");
        stage.setResizable(false);

        ScrollPane spane = new ScrollPane();

        BorderPane pane = new BorderPane();
        VBox vbox = new VBox();
        HBox hbox;

        for (int i = 0; i < Classroom.students.size(); i += 4) {
            hbox = new HBox();
            VBox student = new VBox ();
            Label stu = new Label ("\tStudent");
            stu.setStyle("-fx-font-size: 14;");
            Label name = new Label ("\t" + Classroom.students.get(i + 1) +
                    ", " + Classroom.students.get(i));
            name.setStyle("-fx-font-size: 17");
            student.getChildren().addAll(stu, name);
            student.setSpacing(4.0);
            student.setMinWidth(225);

            noMark[i / 4] = new CheckBox("No Mark");
            mark[i/4] = new TextField();

            noMark[i/4].setOnAction(e -> {
                for (int j = 0; j < noMark.length; j++) {
                    if (noMark[j].isSelected()) {
                        mark[j].setText("");
                        mark[j].setDisable(true);
                    }
                    else
                        mark[j].setDisable(false);
                }
            });

            Label denom = new Label ("/" + denominator);
            denom.setStyle("-fx-font-size: 16");


            hbox.setSpacing(23.0);
            hbox.getChildren().addAll(student, noMark[i/4], mark[i/4], denom);
            hbox.setPadding(new Insets(20, 0, 0, 0));
            vbox.getChildren().add(hbox);
        }
        spane.setContent(vbox);
        spane.setPrefSize(535, 400);
        spane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS );
        spane.setFitToHeight(true);

        pane.setCenter(spane);

        Button okButton = new Button("OK");
        Button cancelButton = new Button ("Cancel");

        okButton.setOnAction(e -> {
            if (valid()) {
                save();
                stage.close();
            }
            else {
                displayError();
            }
        });
        cancelButton.setOnAction(e -> {
            stage.close();
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets (10, 10, 10, 10));
        HBox buttons = new HBox();
        buttons.setSpacing(5);
        buttons.getChildren().addAll(cancelButton, okButton);
        grid.setConstraints(buttons,0,0);
        grid.setAlignment(Pos.CENTER_RIGHT);
        grid.getChildren().add(buttons);

        pane.setBottom(grid);

        Scene scene = new Scene(pane, 615, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public static void save() {
        for (int i = 0; i < Classroom.students.size(); i+=4) {
            if (!noMark[i/4].isSelected()) {
                try {
                    File directory = new File (Classroom.className);

                    if (!directory.exists()){
                        try {
                            directory.mkdir();
                        } catch (SecurityException err) {
                            System.exit(0);
                        }
                    }

                    FileWriter file = new FileWriter ((Classroom
                            .className + "/"+ Classroom.students.get(i + 2)
                            + ".txt"), true);
                    BufferedWriter buffer = new BufferedWriter (file);

                    buffer.write(nameOfEval);
                    buffer.newLine();
                    buffer.write(mark[i/4].getText());
                    buffer.newLine();
                    buffer.write("" + denomor);
                    buffer.newLine();
                    buffer.write("" + weight);
                    buffer.newLine();
                    buffer.close();
                } catch (IOException err) {
                    // Empty Catch
                }
            }
        }
    }
    public static boolean valid() {
        try {
           for (int i = 0; i < Classroom.students.size(); i+=4) {
               if (!noMark[i/4].isSelected())
                   if (Integer.parseInt(mark[i/4].getText()) < 0)
                       return false;
           }
        } catch (NumberFormatException e) {
            return false;
        }
        catch (InputMismatchException e) {
            return false;
        }
        return true;
    }
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
