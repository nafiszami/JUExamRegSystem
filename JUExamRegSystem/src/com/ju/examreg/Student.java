package storage;

import dob.DateOfBirth; // Import the new DateOfBirth class

public class Student {
    private String name, rollNo, hallName, studentId, department, batch, gender , mobile;
    private DateOfBirth dob; // Added DateOfBirth field

    public Student(String name, String rollNo, String hallName, String studentId, String department, String batch, String gender, DateOfBirth dob) {
        this.name = name;
        this.rollNo = rollNo;
        this.hallName = hallName;
        this.studentId = studentId;
        this.department = department;
        this.batch = batch;
        this.gender = gender;
        this.dob = dob; // Initialize the DateOfBirth
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNo + ", Hall: " + hallName + ", ID: " + studentId +
                ", Department: " + department + ", Batch: " + batch + ", Gender: " + gender + ", DOB: " + dob + ",Mobile No: ";
    }
}
