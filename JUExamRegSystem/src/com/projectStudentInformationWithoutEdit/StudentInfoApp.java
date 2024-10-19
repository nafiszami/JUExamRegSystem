package com.projectStudentInformationWithoutEdit;

public class StudentInfoApp {
    public static void main(String[] args) {
        // Create a sample student object
        Student student = new Student(
                "Anik Bose ",
                "Mr. Bose",
                "Mrs. Bose",
                "01-01-2000",
                "S12345",
                "67890",
                "MH Hall",
                "Computer Science and Engineering ",
                "2023-2024",
                "Batch 53"
        );

        // Launch the StudentInfoFrame with the sample student
        new StudentInfoFrame(student);
    }
}
