package com.example.timetablejava;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudent {
    @FXML
    private TextField studentNameField;

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/timetable";
        String user = "root";
        String password = "sherhassan";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @FXML
    private void handleAddStudent() {
        String studentName = studentNameField.getText();
        if (studentName == null || studentName.trim().isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return;
        }

        String sql = "INSERT INTO Students (Name) VALUES (?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentName);
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleRemoveStudent() {
        String studentName = studentNameField.getText();
        if (studentName == null || studentName.trim().isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return;
        }

        String sql = "DELETE FROM Students WHERE Name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentName);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
