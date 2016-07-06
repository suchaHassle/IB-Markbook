package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import javafx.scene.*;
import javafx.geometry.*;

public class AlertBox {

    static boolean result;

    /**
     * This method displays an alert on whether to user wishes to perform
     * whatever action they did
     *
     * @return Whether the user wishes to continue with whatever action
     */
    public static boolean display()
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Alert - IB Markbook");
        window.setResizable(false);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label label = new Label ("Are you sure?");

        Button buttonYes = new Button ("Yes");
        Button buttonNo = new Button ("No");

        buttonYes.setOnAction(e -> {
            result = true;
            window.close();
        });

        buttonNo.setOnAction(e -> {
            result = false;
            window.close();
        });

        GridPane.setConstraints(label, 1, 1);
        GridPane.setConstraints(buttonYes, 1, 2);
        GridPane.setConstraints(buttonNo, 2, 2);
        grid.getChildren().addAll(label, buttonYes, buttonNo);

        Scene scene = new Scene(grid, 175, 100);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
}
