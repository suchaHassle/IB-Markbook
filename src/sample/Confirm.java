/**
 * This class confirms whether or not the login information is, meeting the
 * correct character count, character range, etc.
 */
package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Confirm {

    private static final String VALID_USERNAME = "^[a-zA-Z0-9_-]{4,32}$";
    private static final String VALID_PASSWORD = "^[a-zA-Z0-9_-]{4,32}$";
    private static Pattern patternUsername = Pattern.compile(VALID_USERNAME);
    private static Pattern patternPassword = Pattern.compile(VALID_PASSWORD);
    private static Matcher matcher;

    /**
     * This method returns the a display telling the user that they have no
     * inputed the correct information
     *
     * @param usernameField - Username field in creating an account prompt
     * @param passwordField - Password field in creating an account prompt
     * @param passwordConfirm - Password Confirmation
     * @return a boolean whether the inputted data is valid or not
     * @throws Exception
     */
    public static boolean display(TextField usernameField, PasswordField
            passwordField, PasswordField passwordConfirm) throws Exception {

        String username = usernameField.getText();
        String password = passwordField.getText();
        String password2 = passwordConfirm.getText();
        boolean valid = true;
        ArrayList<Label> invalidFields = new ArrayList<>();

        if (!validUsername(username)) {
            valid = false;
            Label label = new Label("*Username must be 4 to 32 characters, " +
                    "a-z, A-Z, 0-9 only.");
            invalidFields.add(label);
        }
        if (!validPassword(password)) {
            valid = false;
            Label label = new Label("*Password must be 4 to 32 characters, " +
                    "a-z, A-Z, 0-9 only.");
            invalidFields.add(label);
        }
        if (!password.equals(password2)) {
            valid = false;
            Label label = new Label("Password does not match!");
            invalidFields.add(label);
        }

        if (!valid) {
            Stage window = new Stage();

            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Error");
            window.setResizable(false);

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(10);
            grid.setHgap(10);

            for (int i = 0; i < invalidFields.size(); i++)
            {
                GridPane.setConstraints(invalidFields.get(i), 1, i + 1);
                grid.getChildren().add(invalidFields.get(i));
            }

            Button button = new Button("Close");

            button.setOnAction(e -> {
                window.close();
            });

            grid.add(button, 1, invalidFields.size() + 2);

            Scene scene = new Scene(grid, 530, 220);
            window.setScene(scene);
            window.showAndWait();

            return false;
        }
        else {
            try {
                FileWriter file = new FileWriter("class.IB");
                BufferedWriter buffer = new BufferedWriter(file);

                buffer.write(username);
                buffer.newLine();
                buffer.write(password);

                buffer.close();
            } catch (IOException err) {
                System.out.print ("ERROR WRITING TO FILE CLASS.IB");
            }
            return true;
        }
    }

    /**
     * This boolean method determines whether the username is valid and
     * returns false if not
     *
     * @param username
     * @return whether the username is valid
     */
    private static boolean validUsername (String username) {
        matcher = patternUsername.matcher(username);
        return matcher.matches();
    }

    /**
     * This boolean method determines whether the password is valid and
     * returns false if not
     *
     * @param password
     * @return whether the password is valid
     */
    private static boolean validPassword (String password) {
        matcher = patternPassword.matcher(password);
        return matcher.matches();
    }
}
