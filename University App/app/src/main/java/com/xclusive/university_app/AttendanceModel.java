package com.xclusive.university_app;

class AttendanceModel {
    //8 attributes
    String attendancepapercode,facultyname,papername,facultydutyleaver,
            section,rollno,daysdate,totalsbents,totalpresents,totalstudents,
            totaldays;

    public AttendanceModel(String attendancepapercode, String facultyname, String papername,
                           String facultydutyleaver, String section, String rollno, String daysdate,
                           String totalsbents, String totalpresents, String totalstudents, String totaldays) {
        this.attendancepapercode = attendancepapercode;
        this.facultyname = facultyname;
        this.papername = papername;
        this.facultydutyleaver = facultydutyleaver;
        this.section = section;
        this.rollno = rollno;
        this.daysdate = daysdate;
        this.totalsbents = totalsbents;
        this.totalpresents = totalpresents;
        this.totalstudents = totalstudents;
        this.totaldays = totaldays;
    }

    public String getAttendancepapercode() {
        return attendancepapercode;
    }

    public void setAttendancepapercode(String attendancepapercode) {
        this.attendancepapercode = attendancepapercode;
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }

    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    public String getFacultydutyleaver() {
        return facultydutyleaver;
    }

    public void setFacultydutyleaver(String facultydutyleaver) {
        this.facultydutyleaver = facultydutyleaver;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getDaysdate() {
        return daysdate;
    }

    public void setDaysdate(String daysdate) {
        this.daysdate = daysdate;
    }

    public String getTotalsbents() {
        return totalsbents;
    }

    public void setTotalsbents(String totalsbents) {
        this.totalsbents = totalsbents;
    }

    public String getTotalpresents() {
        return totalpresents;
    }

    public void setTotalpresents(String totalpresents) {
        this.totalpresents = totalpresents;
    }

    public String getTotalstudents() {
        return totalstudents;
    }

    public void setTotalstudents(String totalstudents) {
        this.totalstudents = totalstudents;
    }

    public String getTotaldays() {
        return totaldays;
    }

    public void setTotaldays(String totaldays) {
        this.totaldays = totaldays;
    }
}
