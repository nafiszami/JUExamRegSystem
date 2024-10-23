package storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static List<Student> studentList = new ArrayList<>();

    public static void saveStudent(Student student) {
        studentList.add(student);
    }

    public static String viewRegistrations() {
        if (studentList.isEmpty()) {
            return "No registrations found.";
        }

        StringBuilder registrations = new StringBuilder();
        for (Student student : studentList) {
            registrations.append(student.toString()).append("\n");
        }
        return registrations.toString();
    }

    // Method to view registrations from the file
    public static String viewRegistrationsFromFile(String fileName) {
        StringBuilder registrations = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                registrations.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error reading the file.";
        }

        if (registrations.length() == 0) {
            return "No registrations found.";
        }

        return registrations.toString();
    }
}
