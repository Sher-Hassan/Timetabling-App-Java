package com.example.timetablejava;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTeacher {
    @FXML
    private TextField teacherNameField;

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
    private void handleAddTeacher() {
        String teacherName = teacherNameField.getText();
        if (teacherName == null || teacherName.trim().isEmpty()) {
            System.out.println("Teacher name cannot be empty.");
            return;
        }

        String sql = "INSERT INTO Teachers (Name) VALUES (?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teacherName);
            pstmt.executeUpdate();
            System.out.println("Teacher added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleRemoveTeacher() {
        String teacherName = teacherNameField.getText();
        if (teacherName == null || teacherName.trim().isEmpty()) {
            System.out.println("Teacher name cannot be empty.");
            return;
        }

        String sql = "DELETE FROM Teachers WHERE Name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teacherName);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Teacher removed successfully.");
            } else {
                System.out.println("Teacher not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
