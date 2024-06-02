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
import java.sql.ResultSet;
import java.sql.Statement;

public class SectionWise {
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
    @FXML
    private TextField sectionField;

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

        loadData();
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/timetable";
        String user = "root";
        String password = "sherhassan";

        String query = "SELECT t.TimetableID, s.Name AS StudentName, te.Name AS TeacherName, r.Name AS RoomName, c.Name AS CourseName, t.Day, t.StartTime, t.EndTime, t.Section " +
                "FROM Timetable t " +
                "JOIN Students s ON t.StudentID = s.StudentID " +
                "JOIN Teachers te ON t.TeacherID = te.TeacherID " +
                "JOIN Rooms r ON t.RoomID = r.RoomID " +
                "JOIN Courses c ON t.CourseID = c.CourseID";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String section = sectionField.getText();
        if (section == null || section.trim().isEmpty()) {
            // Show all data if no section is entered
            loadData();
            return;
        }

        String url = "jdbc:mysql://localhost:3306/timetable";
        String user = "root";
        String password = "sherhassan";

        String query = "SELECT t.TimetableID, s.Name AS StudentName, te.Name AS TeacherName, r.Name AS RoomName, c.Name AS CourseName, t.Day, t.StartTime, t.EndTime, t.Section " +
                "FROM Timetable t " +
                "JOIN Students s ON t.StudentID = s.StudentID " +
                "JOIN Teachers te ON t.TeacherID = te.TeacherID " +
                "JOIN Rooms r ON t.RoomID = r.RoomID " +
                "JOIN Courses c ON t.CourseID = c.CourseID " +
                "WHERE t.Section = '" + section + "'";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
