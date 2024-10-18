

package storage;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static List<Student> studentList = new ArrayList<>();

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
}

