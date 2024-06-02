package com.example.timetablejava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveClass {
    @FXML
    private TextField classIDField;
    @FXML
    private TableView<Timetable> timetableTable;
    @FXML
    private TableColumn<Timetable, Integer> colID;
    @FXML
    private TableColumn<Timetable, String> colStudentName;
    @FXML
    private TableColumn<Timetable, String> colTeacherName;
    @FXML
    private TableColumn<Timetable, String> colRoomName;
    @FXML
    private TableColumn<Timetable, String> colCourseName;
    @FXML
    private TableColumn<Timetable, String> colDay;
    @FXML
    private TableColumn<Timetable, String> colStartTime;
    @FXML
    private TableColumn<Timetable, String> colEndTime;
    @FXML
    private TableColumn<Timetable, String> colSection;

    private ObservableList<Timetable> data;

    public void initialize() {
        data = FXCollections.observableArrayList();
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        loadTimetableData();
    }

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

    private void loadTimetableData() {
        String query = "SELECT t.TimetableID, s.Name AS StudentName, te.Name AS TeacherName, r.Name AS RoomName, c.Name AS CourseName, t.Day, t.StartTime, t.EndTime, t.Section " +
                "FROM Timetable t " +
                "JOIN Students s ON t.StudentID = s.StudentID " +
                "JOIN Teachers te ON t.TeacherID = te.TeacherID " +
                "JOIN Rooms r ON t.RoomID = r.RoomID " +
                "JOIN Courses c ON t.CourseID = c.CourseID";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            data.clear();
            while (rs.next()) {
                data.add(new Timetable(
                        rs.getInt("TimetableID"),
                        rs.getString("StudentName"),
                        rs.getString("TeacherName"),
                        rs.getString("RoomName"),
                        rs.getString("CourseName"),
                        rs.getString("Day"),
                        rs.getString("StartTime"),
                        rs.getString("EndTime"),
                        rs.getString("Section")
                ));
            }

            timetableTable.setItems(data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleRemoveClass() {
        String classID = classIDField.getText();
        if (classID == null || classID.trim().isEmpty()) {
            System.out.println("Class ID must be provided.");
            return;
        }

        String query = "DELETE FROM Timetable WHERE TimetableID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(classID));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Class removed successfully.");
                loadTimetableData(); // Refresh the table after removing the class
            } else {
                System.out.println("Class ID not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
