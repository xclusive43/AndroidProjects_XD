package com.xclusive.university_app;

class AttendanceModel2 {
    private String presenttext,facultynametext,datetext,timetext;

    public AttendanceModel2(String presenttext, String facultynametext, String datetext, String timetext) {
        this.presenttext = presenttext;
        this.facultynametext = facultynametext;
        this.datetext = datetext;
        this.timetext = timetext;
    }

    public String getPresenttext() {
        return presenttext;
    }

    public void setPresenttext(String presenttext) {
        this.presenttext = presenttext;
    }

    public String getFacultynametext() {
        return facultynametext;
    }

    public void setFacultynametext(String facultynametext) {
        this.facultynametext = facultynametext;
    }

    public String getDatetext() {
        return datetext;
    }

    public void setDatetext(String datetext) {
        this.datetext = datetext;
    }

    public String getTimetext() {
        return timetext;
    }

    public void setTimetext(String timetext) {
        this.timetext = timetext;
    }
}
