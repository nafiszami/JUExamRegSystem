//package com.ju.examreg;

package storage;

public class Student {
    private String name, rollNo, hallName, studentId, department, batch;

    public Student(String name, String rollNo, String hallName, String studentId, String department, String batch) {
        this.name = name;
        this.rollNo = rollNo;
        this.hallName = hallName;
        this.studentId = studentId;
        this.department = department;
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNo + ", Hall: " + hallName + ", ID: " + studentId +
                ", Department: " + department + ", Batch: " + batch;
    }
}

