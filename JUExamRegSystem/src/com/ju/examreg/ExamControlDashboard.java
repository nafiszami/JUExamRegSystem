package com.ju.examreg;

import javax.swing.*;
import java.awt.*;

public class ExamControlDashboard extends JFrame {
    public ExamControlDashboard() {
        setTitle("Exam Control Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton addExamButton = new JButton("Add Exam");
        JButton addSubjectButton = new JButton("Add Subject");
        JButton linkSubjectButton = new JButton("Link Subject");
        JButton approvedStudentsButton = new JButton("Approved Students");

        addExamButton.addActionListener(e -> new AddExamWindow().setVisible(true));
        addSubjectButton.addActionListener(e -> new AddSubjectWindow().setVisible(true));
        linkSubjectButton.addActionListener(e -> new LinkSubjectWindow().setVisible(true));
        approvedStudentsButton.addActionListener(e -> new ApprovedStudentsWindow().setVisible(true));

        add(addExamButton);
        add(addSubjectButton);
        add(linkSubjectButton);
        add(approvedStudentsButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamControlDashboard().setVisible(true));
    }
}
