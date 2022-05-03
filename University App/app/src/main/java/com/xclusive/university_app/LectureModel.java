package com.xclusive.university_app;

class LectureModel {

    String time, facultyname, lecturebody,lecturedate;

    public LectureModel(String time, String facultyname, String lecturebody,String lecturedate) {
        this.time = time;
        this.facultyname = facultyname;
        this.lecturebody = lecturebody;
        this.lecturedate = lecturedate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }

    public String getLecturebody() {
        return lecturebody;
    }

    public void setLecturebody(String lecturebody) {
        this.lecturebody = lecturebody;
    }

    public String getLecturedate() {
        return lecturedate;
    }

    public void setLecturedate(String lecturedate) {
        this.lecturedate = lecturedate;
    }
}
