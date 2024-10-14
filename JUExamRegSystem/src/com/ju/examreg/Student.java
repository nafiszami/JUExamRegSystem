package storage;

public class Student {
    private String name, rollNo, hallName, studentId, department, batch, gender; // Added gender

    public Student(String name, String rollNo, String hallName, String studentId, String department, String batch, String gender) {
        this.name = name;
        this.rollNo = rollNo;
        this.hallName = hallName;
        this.studentId = studentId;
        this.department = department;
        this.batch = batch;
        this.gender = gender; // Initialize gender
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNo + ", Hall: " + hallName + ", ID: " + studentId +
                ", Department: " + department + ", Batch: " + batch + ", Gender: " + gender; // Include gender
    }
}
