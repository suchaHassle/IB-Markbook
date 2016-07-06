/**
 * If the user does not have a username or password, this class is used to
 * allow the user to create one
 */
package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewLogin {

    /**
     * This is the interface that prompts the user for information that will
     * allow them to login.
     *
     * @throws Exception
     */
    public static void newLogin () throws Exception {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle ("IB Markbook");
        window.setResizable(false);
        window.setOnCloseRequest(e -> System.exit(0));

        Label title = new Label();
        title.setText("Create new login");
        Label username = new Label();
        username.setText("Username: ");
        Label password = new Label();
        password.setText("Password: ");
        Label passwordConfirmText = new Label();
        passwordConfirmText.setText("Confirm Password: ");
        Button cancelButton = new Button ("Cancel");
        cancelButton.setOnAction (e -> System.exit(0));
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordFieldConfirm = new PasswordField();
        Button okButton = new Button("OK");
        okButton.setOnAction(e-> {
            try {
                if (Confirm.display(usernameField, passwordField,
                        passwordFieldConfirm)) {
                    window.close();
                }
            } catch (Exception e1) {
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
        GridPane.setConstraints(username, 1, 2);
        GridPane.setConstraints(usernameField, 2,2);
        GridPane.setConstraints(password, 1, 3);
        GridPane.setConstraints(passwordField, 2, 3);
        GridPane.setConstraints(passwordConfirmText, 1, 4);
        GridPane.setConstraints(passwordFieldConfirm, 2, 4);
        GridPane.setConstraints(hbox, 2, 6);

        layout.getChildren().addAll(title, username, usernameField, password,
                passwordField, passwordConfirmText, passwordFieldConfirm,
                hbox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}