package com.projectStudentInformationWithoutEdit;

public class StudentInfoApp {
    public static void main(String[] args) {
        // Create a sample student object
        Student student = new Student(
                "John Doe",
                "Mr. Doe",
                "Mrs. Doe",
                "01-01-2000",
                "S12345",
                "R67890",
                "XYZ Hall",
                "Computer Science",
                "2020-2024",
                "Batch 20"
        );

        // Launch the StudentInfoFrame with the sample student
        new StudentInfoFrame(student);
    }
}
