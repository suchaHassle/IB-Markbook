/**
 * This class determines whether or not the user has any outliers in their
 * marks that may hurt their average
 */
package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Outliers {

    /**
     * Determines whether the student has any outliers
     *
     * @param studentNumber
     * @param className
     * @return returns whether the student has outliers
     */
    public static boolean determineStudent(String studentNumber, String
                                           className) {
        ArrayList<Double> marks = new ArrayList<>();
        System.out.println ("TEST");

        FileReader file = null;
        try {
            file = new FileReader(className + "/" +
                    studentNumber + ".txt");
            BufferedReader buffer = new BufferedReader(file);

            buffer.readLine();
            buffer.readLine();
            buffer.readLine(); // Skips reading the next line

            String currentLine = buffer.readLine();
            while (currentLine != null) {
                currentLine = buffer.readLine();
                String otherLine = buffer.readLine();
                double tem = Double.parseDouble(currentLine)/ Double
                        .parseDouble(otherLine);
                marks.add(tem * 100);
                currentLine = buffer.readLine();
                currentLine = buffer.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] mark = new double[marks.size()];
        for (int i = 0; i < marks.size(); i++) {
            mark[i] = marks.get(i);
        }

        double average = 0;

        for (int i = 0; i < mark.length; i++)
            average += mark[i];

        average = average/mark.length;
        double standardDeviation = 0;

        for (int i = 0; i < mark.length; i++) {
            System.out.println (mark[i]);
            standardDeviation += (mark[i] - average) * (mark[i] - average);
        }
        standardDeviation /= mark.length;
        standardDeviation = Math.sqrt(standardDeviation);

        System.out.println (standardDeviation);

        for (int i = 0; i < mark.length; i++) {
            if (mark[i] < average - standardDeviation - standardDeviation)
                return true;
        }

        return false;
    }

    /**
     * Determines each evaluation in their record and returns the ones that
     * are outliers
     *
     * @param studentNumber
     * @param className
     * @return outliers for each evaluation
     */
    public static double[] determineEachEntry(String studentNumber, String
            className) {
        ArrayList<Double> marks = new ArrayList<>();

        try {
            FileReader file = new FileReader(Student.className + "/" +
                    Student.studentNumber + ".txt");
            BufferedReader buffer = new BufferedReader(file);
            buffer.readLine();
            buffer.readLine();
            buffer.readLine(); // Skips reading the next line

            String currentLine = buffer.readLine();
            while (currentLine != null) {
                currentLine = buffer.readLine();
                marks.add(Double.parseDouble(currentLine)/ Double
                        .parseDouble(buffer.readLine()));
                currentLine = buffer.readLine();
                currentLine = buffer.readLine();
            }
        } catch (IOException error) {
            System.out.println ("File does not exist!");
        }

        double[] mark = new double[marks.size()];
        for (int i = 0; i < marks.size(); i++) {
            mark[i] = marks.get(i);
        }

        double average = 0;

        for (int i = 0; i < mark.length; i++)
            average += mark[i];

        average = average/mark.length;
        double standardDeviation = 0;

        for (int i = 0; i < mark.length; i++) {
            System.out.println (mark[i]);
            standardDeviation += (mark[i] - average) * (mark[i] - average);
        }
        standardDeviation /= mark.length;
        standardDeviation = Math.sqrt(standardDeviation);

        System.out.println (standardDeviation);

        for (int i = 0; i < mark.length; i++) {
            if (mark[i] < average - standardDeviation - standardDeviation)
                mark[i] *= -1;
        }

        return mark;
    }
}
