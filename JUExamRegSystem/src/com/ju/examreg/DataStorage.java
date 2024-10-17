package storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static final String FILE_NAME = "registered_students.txt";

    public static void saveStudent(Student student) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(student.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String viewRegistrations() {
        StringBuilder registrations = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                registrations.append(line).append("\n");
            }
        } catch (IOException e) {
            return "No registrations found.";
        }

        return registrations.length() > 0 ? registrations.toString() : "No registrations found.";
    }

    public static void resetFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.print(""); // Clear the contents of the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
