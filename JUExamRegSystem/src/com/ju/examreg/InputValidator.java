//package com.ju.examreg;

package validation;

public class InputValidator {
    public static boolean validateFields(String name, String rollNo, String hallName, String studentId, String department, String batch) {
        // Check if any field is empty
        if (name.isEmpty() || rollNo.isEmpty() || hallName.isEmpty() || studentId.isEmpty() || department.isEmpty() || batch.isEmpty()) {
            return false;
        }

        // Check if student ID is numeric
        try {
            Integer.parseInt(studentId);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}

