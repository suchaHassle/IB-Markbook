/**
 * This class prompts the user to let them know that their information that
 * changes have been made to OSSD marks
 */
package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OSSDConfirmation {

    /**
     * Tells the user that changes have been made with Ontario boundaries
     *
     * @param displayMessage
     * @throws Exception
     */
    public static void display (String displayMessage) throws Exception {
        Stage window = new Stage ();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle ("IB Markbook");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(displayMessage);
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
