package com.ju.examreg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Custom table model to prevent cell editing
class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Prevent editing of cells
    }
}

public class ApprovedStudentsWindow extends JFrame {
    private JComboBox<String> examComboBox = new JComboBox<>();
    private JTable studentTable = new JTable();
    private NonEditableTableModel tableModel = new NonEditableTableModel(new Object[]{"Student ID", "Name", "Exam Roll", "Hall", "Batch"}, 0);
    private JButton loadStudentsButton = new JButton("Load Approved Students");
    private JButton logoutButton = new JButton("Logout");

    public ApprovedStudentsWindow() {
        setTitle("Approved Students");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set a custom look and feel
        UIManager.put("Button.background", new Color(70, 130, 180));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 14));

        // Panel for controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder("Select Exam"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Exam selection label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(new JLabel("Select Exam:"), gbc);

        // Exam selection combo box
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(examComboBox, gbc);

        // Load students button
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        controlPanel.add(loadStudentsButton, gbc);

        // Logout button
        gbc.gridx = 3;
        controlPanel.add(logoutButton, gbc);

        add(controlPanel, BorderLayout.NORTH);
        studentTable.setModel(tableModel);
        studentTable.setFillsViewportHeight(true);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        studentTable.getTableHeader().setBackground(new Color(70, 130, 180));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.setBackground(Color.WHITE);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load exams into the combo box
        loadExams();

        // Action listeners
        loadStudentsButton.addActionListener(e -> loadApprovedStudents());
        logoutButton.addActionListener(e -> {
            new HallWindow().setVisible(true); // Implement your HallWindow as needed
            dispose();
        });
    }

    private void loadExams() {
        examComboBox.removeAllItems();
        String sql = "SELECT exam_id, semester, department_name, exam_year, exam_type FROM exam_info"; // Adjust according to your exams table

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String examId = rs.getString("exam_id");
                String semester = rs.getString("semester");
                String department = rs.getString("department_name");
                String year = rs.getString("exam_year");
                String type = rs.getString("exam_type");

                examComboBox.addItem("ID: " + examId + " | Semester: " + semester + " | Dept: " + department +
                        " | Year: " + year + " | Type: " + type);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading exams: " + ex.getMessage());
        }
    }

    private void loadApprovedStudents() {
        tableModel.setRowCount(0); // Clear previous data

        String selectedExam = (String) examComboBox.getSelectedItem();
        if (selectedExam == null) {
            JOptionPane.showMessageDialog(this, "Please select an exam first.");
            return;
        }

        String examId = selectedExam.split("\\|")[0].split(":")[1].trim(); // Extract exam_id

        String sql = "SELECT s.student_id, s.student_name, s.exam_roll, s.hall, s.batch " +
                "FROM students s " +
                "JOIN exam_registrations r ON s.student_id = r.student_id " +
                "WHERE r.exam_id = ? " +
                "AND r.department_approval = 'Approved' " +
                "AND r.hall_approval = 'Approved' " +
                "AND r.registrar_approval = 'Approved'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, examId); // Set the exam_id in the query
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                int examRoll = rs.getInt("exam_roll");
                String hall = rs.getString("hall");
                int batch = rs.getInt("batch");

                tableModel.addRow(new Object[]{studentId, studentName, examRoll, hall, batch});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading approved students: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ApprovedStudentsWindow().setVisible(true));
    }
}
