/**
 * This is the main class that launches the login page
 */
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    public static Stage window;
    public static final String CLASS_FILE = "class.IB";
    static ArrayList<String> classesID = new ArrayList<String>();
    static ArrayList<ArrayList<String>> classes = new ArrayList<ArrayList<String>>();
    static int[] ontarioMarks = new int[14];
    static double[][] boundaries;
    public static String username;
    public static String password;

    /**
     * Checks to see if the data exists and prompts the user to create a
     * username and password if not. After authentication, the user may then
     * proceed to the main menu
     *
     * @param primaryStage - required to initialize a new FXML file
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FileReader file;
        BufferedReader buffer;

        /*
         * If the user data does not exist, the program will prompt with an
         * error message and proceed to asking the user to create a new login
         */
        if (!readData()) {
            NewLogin.newLogin();
            try {
                file = new FileReader(CLASS_FILE);
                buffer = new BufferedReader (file);

                username = buffer.readLine();
                password = buffer.readLine();

                buffer.close();
            } catch (IOException err) {
                System.out.print ("ERROR 101");
            }

            try {
                Parent root = (Parent) new FXMLLoader(
                        Main.class.getResource("ontario.fxml")).load();
                window = new Stage();
                window.setTitle("IB Markbook");
                window.setOnCloseRequest(e -> {
                    File fileNow = new File (CLASS_FILE);
                    fileNow.delete();
                    System.exit(0);
                });
                window.setScene(new Scene(root));
                window.showAndWait();

                OSSDConfirmation.display("Account successfully created!");

                window = new Stage();
                root = FXMLLoader.load(getClass().getResource
                        ("startup.fxml"));
                window.setTitle("IB Markbook");
                window.setScene(new Scene(root, 450, 300));
                window.show();
            }
            catch (IOException e) {
                System.out.println("Error");
            }
        }
        else {
            window = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource
                    ("startup.fxml"));
            window.setTitle("IB Markbook");
            window.setScene(new Scene(root, 450, 300));
            window.show();
        }
    }

    /**
     *  Searches for the necessary files and stores the data
     */
    public static boolean readData () {
        BufferedReader buffer;
        String classID;
        String currentLine;
        FileReader file;
        int test;
        int counter = 0;

        classID = "";

		/*
		 * Checks to see if the data for all classes exists, if it doesn't
		 * it creates a new one
		 */
        try {
            file = new FileReader(CLASS_FILE);
            buffer = new BufferedReader (file);

            username = buffer.readLine();
            password = buffer.readLine();
            currentLine = buffer.readLine();

            while (currentLine != null) {
                try {
                    test = Integer.parseInt(currentLine);
                    ontarioMarks[counter++] = test;
                    currentLine = buffer.readLine();
                }
                catch (NumberFormatException err) {
                    classesID.add(currentLine);
                    currentLine = buffer.readLine();
                }
            }

            buffer.close();
        }

        /*
         * If file doesn't exist, creates a new "class.IB" file and prompts
         * the user to input a new password and username
         */
        catch (Exception err) {
            try {
                ErrorBox.display();
                System.out.print ("ERROR");
                return false;
            }
            catch (Exception error) {
                System.out.print ("EERRROR2");
                // Empty catch
            }
        }

        boundaries = new double [classesID.size()][14];

		/*
		 * With all the class IDs, this will check and take all the student
		 * info.If not, it creates new classID IB files.
		 */
        for (int i = 0; i < classesID.size(); i++) {
            try {
                classID = classesID.get(i) + ".IB";
                file = new FileReader(classID);
                buffer = new BufferedReader (file);

                // Checks for IB grade boundaries
                for (int j = 0; j < 14; j++) {
                    currentLine = buffer.readLine();
                    boundaries[i][j] = Double.parseDouble(currentLine);
                }

                // Check if there  are students that exists
                classes.add(new ArrayList<String>());
                currentLine = buffer.readLine();

                while (currentLine != null) {
                    classes.get(i).add(currentLine);
                    currentLine = buffer.readLine();
                }
            }
            // If the IB file does not exist, it creates a new IB file.
            catch (FileNotFoundException err) {
                try {
                    FileWriter newFile = new FileWriter (classID);
                    BufferedWriter buff = new BufferedWriter(newFile);

                    for (int j = 0; j < 13; j++) {
                        buff.write("-1");
                        buff.newLine();
                    }
                    buff.write("-1");

                    buff.close();

                    classes.add(new ArrayList<String>());
                }
                catch (IOException error) {
                    // Empty Catch
                }
            }
            /*
             * For any other errors, there is an error in the code and program
             * terminates
             */
            catch (IOException error) {
                System.exit(0);
            }
        }
        return true;
    }

    /**
     * Main Method
     *
     * @param args
     */
    public static void main(String[] args) {launch(args);}
}
