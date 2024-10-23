package storage;

import dob.DateOfBirth;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static List<Student> studentList = new ArrayList<>();
    private static final String FILE_NAME = "registered_students.txt";

    static {
        // Load data from the file when the class is loaded
        loadStudentsFromFile();
    }

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

    // Method to search registrations by name, roll number, or student ID
    public static String searchRegistrations(String searchTerm) {
        StringBuilder results = new StringBuilder();
        boolean found = false;

        for (Student student : studentList) {
            if (student.getName().equalsIgnoreCase(searchTerm) ||
                    student.getRollNo().equalsIgnoreCase(searchTerm) ||
                    student.getStudentId().equalsIgnoreCase(searchTerm)) {
                results.append(student.toString()).append("\n");
                found = true;
            }
        }

        if (!found) {
            results.append("No registrations found for: ").append(searchTerm);
        }

        return results.toString();
    }

    // Method to load students from the file into memory
    // Method to load students from the file into memory
    private static void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming each student is stored as "Name, Roll No, Hall Name, Student ID, Department, Batch, Mobile No, Day/Month/Year"
                String[] data = line.split(", ");
                if (data.length == 8) {
                    String name = data[0];
                    String rollNo = data[1];
                    String hallName = data[2];
                    String studentId = data[3];
                    String department = data[4];
                    String batch = data[5];
                    String mobileNumber = data[6];
                    String[] dobParts = data[7].split("/");

                    if (dobParts.length == 3) {
                        try {
                            int day = Integer.parseInt(dobParts[0]);
                            int month = Integer.parseInt(dobParts[1]);
                            int year = Integer.parseInt(dobParts[2]);
                            DateOfBirth dob = new DateOfBirth(day, month, year);

                            Student student = new Student(name, rollNo, hallName, studentId, department, batch, mobileNumber, dob);
                            studentList.add(student);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid date format for student: " + line);
                        }
                    } else {
                        System.out.println("Date of birth is not in the expected format (Day/Month/Year): " + data[7]);
                    }
                } else {
                    System.out.println("Invalid data format in file: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }
    }

}

