package validation;

public class InputValidator {
    public static boolean validateFields(String name, String rollNo, String hallName, String studentId, String department, String batch, String mobileNumber) {
        if (name.isEmpty() || rollNo.isEmpty() || hallName.isEmpty() || studentId.isEmpty() || department.isEmpty() || batch.isEmpty() || mobileNumber.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(studentId);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
