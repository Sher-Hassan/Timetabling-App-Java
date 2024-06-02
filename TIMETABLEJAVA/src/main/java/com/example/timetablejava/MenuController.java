package com.example.timetablejava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MenuController {

    @FXML
    private void handleViewTimetable(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/view-timetable.fxml", "View Timetable");
    }

    @FXML
    private void handleStudentWise(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/student-wise.fxml", "Student Wise Timetable");
    }

    @FXML
    private void handleTeacherWise(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/teacher-wise.fxml", "Teacher Wise Timetable");
    }
    @FXML
    private void handleSectionWise(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/section-wise.fxml", "Section Wise Timetable");
    }

    @FXML
    private void handleCourseWise(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/course-wise.fxml", "Course Wise Timetable");
    }

    @FXML
    private void handleDayWise(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/day-wise.fxml", "Day Wise Timetable");
    }

    @FXML
    private void handleAddClass(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/add-class.fxml", "Add Class");
    }

    @FXML
    private void handleRemoveClass(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/remove-class.fxml", "Remove Class");
    }

    @FXML
    private void handleAddTeacher(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/add-teacher.fxml", "Add/Remove Teacher");
    }

    @FXML
    private void handleAddStudent(ActionEvent event) throws Exception {
        loadView("/com/example/timetablejava/add-student.fxml", "Add/Remove Student");
    }

    private void loadView(String fxmlPath, String title) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
