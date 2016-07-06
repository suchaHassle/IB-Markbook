/**
 * This class allows users to delete classrooms
 */
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class manageClassrooms extends Main {

    private static TableView<Course> table;

    /**
     * Displays a table of all the classrooms for the user to delete
     *
     * @throws Exception
     */
    public static void display () throws Exception {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Manage Course - IB Markbook");
        window.setResizable(false);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Name Column
        TableColumn<Course, String> column = new TableColumn<>
                ("Classroom");
        column.setMinWidth(290);
        column.setCellValueFactory(new PropertyValueFactory<>
                ("name"));

        // Name Input


        Label label = new Label("Select account:");
        Button buttonBack = new Button("Back");
        Button buttonDelete = new Button("Delete");

        table = new TableView<>();
        table.setItems(getCourse());
        table.getColumns().addAll(column);

        buttonDelete.setOnAction(e -> {
            if (AlertBox.display()) {
                try {
                    deleteClassroom();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonBack.setOnAction(e -> window.close());

        GridPane.setConstraints(label, 1, 1);
        GridPane.setConstraints(table, 1, 2);
        GridPane.setConstraints(buttonDelete, 1, 3);
        GridPane.setConstraints(buttonBack, 1, 4);

        grid.getChildren().addAll(label, table, buttonBack, buttonDelete);

        Scene scene = new Scene(grid, 335, 400);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * This Observable List populates teh table with all the classrooms
     *
     * @return the table with all the classrooms
     */
    public static ObservableList<Course> getCourse() {
        ObservableList<Course> classroom = FXCollections
                .observableArrayList();
        for (int i = 0; i < classesID.size(); i++)
            classroom.add(new Course(classesID.get(i)));

        return classroom;
    }

    /**
     * This method deletes the classroom selected as well as deletes their
     * corresponding files.
     *
     * @throws IOException
     */
    public static void deleteClassroom() throws IOException {
        ObservableList<Course> coursesSelected, allCourses;
        allCourses = table.getItems();
        coursesSelected = table.getSelectionModel().getSelectedItems();
        Course courseSelected = table.getSelectionModel().getSelectedItem();

        coursesSelected.forEach(allCourses::remove);
        String courseName = courseSelected.getName();

        for (int i = 0; i < classesID.size(); i++) {
            if (classesID.get(i).equals(courseName)) {
                classesID.remove(courseName);
            }
        }
        FileWriter file = new FileWriter (CLASS_FILE);
        BufferedWriter buffer = new BufferedWriter(file);

        buffer.write(username);
        buffer.newLine();
        buffer.write(password);

        for (int i = 0; i < 14; i++) {
            buffer.newLine();
            buffer.write(ontarioMarks[i] + "");
        }
        for (int i = 0; i < classesID.size(); i++) {
            buffer.newLine();
            buffer.write(classesID.get(i));
        }
        buffer.close();
        File fileNow = new File (courseName + ".IB");
        fileNow.delete();
    }
 }
