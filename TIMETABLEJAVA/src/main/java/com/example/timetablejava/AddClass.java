package com.example.timetablejava;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddClass {
    @FXML
    private ComboBox<String> studentComboBox;
    @FXML
    private ComboBox<String> teacherComboBox;
    @FXML
    private ComboBox<String> roomComboBox;
    @FXML
    private ComboBox<String> courseComboBox;
    @FXML
    private TextField dayField;
    @FXML
    private TextField startTimeField;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField sectionField;

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
    private void initialize() {
        loadComboBoxData("Students", "Name", studentComboBox);
        loadComboBoxData("Teachers", "Name", teacherComboBox);
        loadComboBoxData("Rooms", "Name", roomComboBox);
        loadComboBoxData("Courses", "Name", courseComboBox);
    }

    private void loadComboBoxData(String tableName, String columnName, ComboBox<String> comboBox) {
        String query = "SELECT " + columnName + " FROM " + tableName;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                comboBox.getItems().add(rs.getString(columnName));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleAddClass() {
        String studentName = studentComboBox.getValue();
        String teacherName = teacherComboBox.getValue();
        String roomName = roomComboBox.getValue();
        String courseName = courseComboBox.getValue();
        String day = dayField.getText();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        String section = sectionField.getText();

        if (studentName == null || teacherName == null || roomName == null || courseName == null ||
                day == null || startTime == null || endTime == null || section == null) {
            System.out.println("All fields must be filled out.");
            return;
        }

        String query = "INSERT INTO Timetable (StudentID, TeacherID, RoomID, CourseID, Day, StartTime, EndTime, Section) " +
                "VALUES ((SELECT StudentID FROM Students WHERE Name = ?), " +
                "(SELECT TeacherID FROM Teachers WHERE Name = ?), " +
                "(SELECT RoomID FROM Rooms WHERE Name = ?), " +
                "(SELECT CourseID FROM Courses WHERE Name = ?), ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentName);
            pstmt.setString(2, teacherName);
            pstmt.setString(3, roomName);
            pstmt.setString(4, courseName);
            pstmt.setString(5, day);
            pstmt.setString(6, startTime);
            pstmt.setString(7, endTime);
            pstmt.setString(8, section);
            pstmt.executeUpdate();

            System.out.println("Class added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
