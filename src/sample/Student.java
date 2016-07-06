package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Student {
    public static BufferedReader buffer;
    public static String className;
    public static FileReader file;
    public static String mark;
    public static ArrayList<String> marks;
    public static String studentFirstName;
    public static String studentLastName;
    public static String studentNumber;
    public static Stage stu;

    /**
     * Constructs a new Student
     *
     * @param studentNumber
     * @param className
     * @throws IOException
     */
    public Student (String studentNumber, String className, String mark)
            throws Exception {
        this.className = className;
        this.studentNumber = studentNumber;
        this.mark = mark;
        average();
        marks = new ArrayList<String>();

        try {
            this.file = new FileReader (className + "/" + studentNumber
                    + ".txt");
            this.buffer = new BufferedReader(this.file);
            this.studentFirstName = buffer.readLine();
            this.studentLastName = buffer.readLine();
            buffer.readLine(); // Skips reading the next line

            String currentLine = buffer.readLine();
            while (currentLine != null) {
                marks.add(currentLine);
                currentLine = buffer.readLine();
            }
        } catch (IOException error) {
            System.out.println ("File does not exist!");
        }
    }

    /**
     * Displays and loads the student dashboard
     *
     * @throws Exception
     */
    public static void display() throws Exception {
        Classroom.stage.close();
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(Classroom.class
                .getResource("student.fxml").openStream());
        stu = new Stage();

        StudentController controller = loader.getController();
        controller.populateTable();

        stu.setOnCloseRequest(e -> {
            try {
                stu.close();
                controller.clearTable();
                Classroom.menu();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        stu.setTitle(studentFirstName + " " + studentLastName
         + " - IB Markbook");
        stu.setScene(new Scene(root));
        stu.show();
    }

    /**
     * Verifies mark and if passes the test, converts the given grade to its
     * IB Score equivalent
     *
     * @return String score: IB Score
     */
    public static String convert () {
        String score = "N/A";

        if (!mark.equals("No Mark") && Classroom.markBoundaries[0] != -1) {
            Convert convert = new Convert(Classroom.markBoundaries,
                    Classroom.ontarioMarks);
            return convert.IBScore(Double.parseDouble(mark));
        }

        return score;
    }

    /**
     * Verifies mark and if passes the test, convert the given grade to its
     * OSSD mark equivalent
     *
     * @return String oMark: Ontario Mark
     */
    public static String ossdConvert() {
        String oMark = "N/A";

        if (!mark.equals("No Mark") && Classroom.markBoundaries[0] != -1) {
            Convert convert = new Convert(Classroom.markBoundaries,
                    Classroom.ontarioMarks);
            return convert.convert(Double.parseDouble(mark)) + "";
        }

        return oMark;
    }

    /**
     * Calculates the average of each student for display
     *
     * @throws Exception
     */
    public static void average() throws Exception {
        double weightDenominator = 0;
        double averageNumerator = 0;
        double newAverage;
        double mark2;
        double outOf;
        String currentLine;
        double weight = 0;

        try {
            FileReader file = new FileReader (className + "/" +
                    studentNumber + ".txt");
            BufferedReader buffer = new BufferedReader (file);

            // Skips reading the first 3 lines
            for (int j = 0; j < 3; j++)
                buffer.readLine();

            currentLine = buffer.readLine();
            while (currentLine != null) {
                currentLine = buffer.readLine();
                mark2 = Double.parseDouble(currentLine);
                currentLine = buffer.readLine();
                outOf = Double.parseDouble(currentLine);
                currentLine = buffer.readLine();
                weight = Double.parseDouble(currentLine) / 100;

                averageNumerator += mark2/outOf * weight;
                weightDenominator += weight;

                // Skips the string
                currentLine = buffer.readLine();
            }
        } catch (IOException err) {
            System.out.println ("\nError 301\n");
        }
        if (weightDenominator != 0) {
            newAverage = averageNumerator / weightDenominator * 100;
            DecimalFormat format = new DecimalFormat("###.#");
            mark = format.format(newAverage);
        }
    }

    /**
     * Reparse new information after each action done by the user.
     * Ex: Entering new evaluation and inserting for calculating average,
     * then use this method to take refresh all information
     */
    public static void reRead() {
        boolean found = false;

        marks = new ArrayList<>();

        try {
            file = new FileReader (className + "/" + studentNumber
                    + ".txt");
            buffer = new BufferedReader(file);
            studentFirstName = buffer.readLine();
            studentLastName = buffer.readLine();
            buffer.readLine(); // Skips reading the next line

            String currentLine = buffer.readLine();
            while (currentLine != null) {
                found = true;
                marks.add(currentLine);
                currentLine = buffer.readLine();
            }
            if (!found)
                mark = "No Mark";
        } catch (IOException error) {
            System.out.println ("File does not exist!");
        }
    }
}
