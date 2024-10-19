package com.projectStudentInformationWithoutEdit;

public class Student {
    private String name;
    private String fathersName;
    private String mothersName;
    private String dateOfBirth;
    private String studentId;
    private String examRoll;
    private String hall;
    private String department;
    private String session;
    private String batch;

    public Student(String name, String fathersName, String mothersName, String dateOfBirth, String studentId,
                   String examRoll, String hall, String department, String session, String batch) {
        this.name = name;
        this.fathersName = fathersName;
        this.mothersName = mothersName;
        this.dateOfBirth = dateOfBirth;
        this.studentId = studentId;
        this.examRoll = examRoll;
        this.hall = hall;
        this.department = department;
        this.session = session;
        this.batch = batch;
    }

    // Getters for student details
    public String getName() { return name; }
    public String getFathersName() { return fathersName; }
    public String getMothersName() { return mothersName; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getStudentId() { return studentId; }
    public String getExamRoll() { return examRoll; }
    public String getHall() { return hall; }
    public String getDepartment() { return department; }
    public String getSession() { return session; }
    public String getBatch() { return batch; }
}
