package com.projectStudentInformation;

public class Main {
    public static void main(String[] args) {
        // Create a sample student object with test data
        Student student = new Student(
                "John Doe",
                "Mr. Doe",
                "Mrs. Doe",
                "01-01-2000",
                "12345",
                "54321",
                "XYZ Hall",
                "Computer Science",
                "2023-2024",
                "2018"
        );

        // Display the student info in the StudentInfoFrame window
        new StudentInfoFrame(student);
    }
}
