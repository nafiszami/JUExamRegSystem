package com.projectStudentInformation;

public class Main {
    public static void main(String[] args) {
        // Create a sample student object with test data
        Student student = new Student(
                "Jasim Khan",
                "Mr. Khan",
                "Mrs. Khan",
                "01-01-2003",
                "12345",
                "54321",
                "SRJ Hall",
                "CSE",
                "2022-2022",
                "52"
        );

        // Display the student info in the StudentInfoFrame window

        new StudentInfoFrame(student);
    }
}
