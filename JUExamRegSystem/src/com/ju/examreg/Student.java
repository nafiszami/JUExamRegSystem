package storage;

import dob.DateOfBirth;

public class Student {
    private String name, rollNo, hallName, studentId, department, batch, mobileNumber;
    private DateOfBirth dateOfBirth;

    public Student(String name, String rollNo, String hallName, String studentId, String department, String batch, String mobileNumber, DateOfBirth dateOfBirth) {
        this.name = name;
        this.rollNo = rollNo;
        this.hallName = hallName;
        this.studentId = studentId;
        this.department = department;
        this.batch = batch;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNo + ", Hall: " + hallName + ", ID: " + studentId +
                ", Department: " + department + ", Batch: " + batch + ", Mobile: " + mobileNumber +
                ", Date of Birth: " + dateOfBirth;
    }
}
