/**
 * This class is a new instances of a classroom that display the classroom
 * dashboard, calculates grades, and load individual students
 */
package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Classroom {

    public static double[] markBoundaries;
    public static int[] ontarioMarks = {};
    public static ArrayList<String> students;
    public static ArrayList<ArrayList<String>> studentMarks = new
            ArrayList<>();
    public static String className;
    public static Stage stage;

    /**
     * Constructs a new Classroom that takes:
     *
     * @param ontarioMarks
     * @param className
     */
    public Classroom(int[] ontarioMarks, String className) {
        this.ontarioMarks = new int[ontarioMarks.length];
        this.className = className;
        this.markBoundaries = new double[14];

        for (int i = 0; i < ontarioMarks.length; i++)
            this.ontarioMarks[i] = ontarioMarks[i];
    }

    /**
     * Prints the menu for a specific classroom
     */
    public static void menu() throws Exception {
        readData();
        average();

        stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(Classroom.class
                .getResource("classroom.fxml").openStream());
        ClassroomController controller = loader.getController();
        controller.populateTable();

        stage.setTitle(className + " - IB Markbook");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    /**
     * Checks to see if the data exists, if it doesn't it will create the file
     */
    public static void readData () {
        students = new ArrayList<String>();
        BufferedReader buffer;
        FileReader file;
        String currentLine = "";

        try {
            file = new FileReader(className + ".IB");
            buffer = new BufferedReader(file);

            for (int i = 0; i < 14; i++) {
                markBoundaries[i] = Double.parseDouble(buffer.readLine());
            }

            currentLine = buffer.readLine();

            while (currentLine != null) {
                students.add(currentLine);
                currentLine = buffer.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < students.size(); i = i + 4) {
            try {
                file = new FileReader(className + "/" + students.get(i + 2) +
                        ".txt");
                buffer = new BufferedReader(file);
            } catch (IOException err) {
                File directory = new File (className);

                if (!directory.exists()){
                    try {
                        directory.mkdir();
                    } catch (SecurityException error) {
                        System.exit(0);
                    }
                }

                try {
                    FileWriter fileWriter = new FileWriter(className + "/" +
                            students.get(i + 2) + ".txt");
                    BufferedWriter bufferWriter = new BufferedWriter
                            (fileWriter);

                    bufferWriter.write (students.get(i));
                    bufferWriter.newLine();
                    bufferWriter.write (students.get(i + 1));
                    bufferWriter.newLine();
                    bufferWriter.write (students.get(i + 2));
                    bufferWriter.newLine();

                    bufferWriter.close();
                } catch (IOException error) {
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Calculates the average for every student
     */
    public static void average () {
        for (int i = 0; i < students.size(); i = i + 4) {
            boolean found = false;
            double weightDenominator = 0;
            double averageNumerator = 0;
            double newAverage;
            double mark;
            double outOf;
            String currentLine;
            double weight = 0;

            try {
                FileReader file = new FileReader (className + "/" + students
                        .get(i + 2) + ".txt");
                BufferedReader buffer = new BufferedReader (file);

                // Skips reading the first 3 lines
                for (int j = 0; j < 3; j++)
                    buffer.readLine();

                currentLine = buffer.readLine();
                while (currentLine != null) {
                    found = true;
                    currentLine = buffer.readLine();
                    mark = Double.parseDouble(currentLine);
                    currentLine = buffer.readLine();
                    outOf = Double.parseDouble(currentLine);
                    currentLine = buffer.readLine();
                    weight = Double.parseDouble(currentLine) / 100;

                    averageNumerator += mark/outOf * weight;
                    weightDenominator += weight;

                    // Skips the string
                    currentLine = buffer.readLine();
                }
            } catch (IOException err) {
                System.out.println ("\nError 301\n");
            }
            if (weightDenominator != 0 && found) {
                newAverage = averageNumerator / weightDenominator * 100;
                DecimalFormat format = new DecimalFormat("###.#");
                students.set((i + 3), format.format(newAverage));
            }
            else
                students.set((i+3), "No Mark");
        }
    }
}

