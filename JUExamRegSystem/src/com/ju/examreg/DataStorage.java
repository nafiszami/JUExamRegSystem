package storage;

import dob.DateOfBirth;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static List<Student> studentList = new ArrayList<>();
    private static final String FILE_NAME = "registered_students.txt";

    // Load students from the text file when the class is loaded
    static {
        loadFromFile();
    }

    public static void saveStudent(Student student) {
        studentList.add(student);
        saveToFile(student);
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

    public static String searchStudents(String query) {
        StringBuilder results = new StringBuilder();
        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(query.toLowerCase()) ||
                    student.getRollNo().toLowerCase().contains(query.toLowerCase()) ||
                    student.getStudentId().toLowerCase().contains(query.toLowerCase())) {
                results.append(student.toString()).append("\n");
            }
        }
        return results.length() == 0 ? "No results found." : results.toString();
    }

    private static void saveToFile(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(student.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the format is correct
                String[] data = line.split(", ");
                if (data.length == 8) {
                    String name = data[0].split(": ")[1];
                    String rollNo = data[1].split(": ")[1];
                    String hallName = data[2].split(": ")[1];
                    String studentId = data[3].split(": ")[1];
                    String department = data[4].split(": ")[1];
                    String batch = data[5].split(": ")[1];
                    String mobileNumber = data[6].split(": ")[1];
                    String[] dobParts = data[7].split(": ")[1].split("/");
                    int day = Integer.parseInt(dobParts[0]);
                    int month = Integer.parseInt(dobParts[1]);
                    int year = Integer.parseInt(dobParts[2]);
                    DateOfBirth dob = new DateOfBirth(day, month, year);

                    Student student = new Student(name, rollNo, hallName, studentId, department, batch, mobileNumber, dob);
                    studentList.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetData() {
        studentList.clear();
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            // Clear file contents
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
