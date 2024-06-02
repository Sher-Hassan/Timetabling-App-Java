package com.example.timetablejava;

public class Timetable {
    private int id;
    private String studentName;
    private String teacherName;
    private String roomName;
    private String courseName;
    private String day;
    private String startTime;
    private String endTime;
    private String section;

    public Timetable(int id, String studentName, String teacherName, String roomName, String courseName, String day, String startTime, String endTime, String section) {
        this.id = id;
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.roomName = roomName;
        this.courseName = courseName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSection() {
        return section;
    }
}
