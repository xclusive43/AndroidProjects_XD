package com.xclusive.university_app;

class ExamModel {

    String examhead,exammode,exampapername,examroom,examdate,examtime,examreporttime;

    public ExamModel(String examhead, String exammode, String exampapername, String examroom,
                     String examdate, String examtime, String examreporttime) {
        this.examhead = examhead;
        this.exammode = exammode;
        this.exampapername = exampapername;
        this.examroom = examroom;
        this.examdate = examdate;
        this.examtime = examtime;
        this.examreporttime = examreporttime;
    }

    public String getExamhead() {
        return examhead;
    }

    public void setExamhead(String examhead) {
        this.examhead = examhead;
    }

    public String getExammode() {
        return exammode;
    }

    public void setExammode(String exammode) {
        this.exammode = exammode;
    }

    public String getExampapername() {
        return exampapername;
    }

    public void setExampapername(String exampapername) {
        this.exampapername = exampapername;
    }

    public String getExamroom() {
        return examroom;
    }

    public void setExamroom(String examroom) {
        this.examroom = examroom;
    }

    public String getExamdate() {
        return examdate;
    }

    public void setExamdate(String examdate) {
        this.examdate = examdate;
    }

    public String getExamtime() {
        return examtime;
    }

    public void setExamtime(String examtime) {
        this.examtime = examtime;
    }

    public String getExamreporttime() {
        return examreporttime;
    }

    public void setExamreporttime(String examreporttime) {
        this.examreporttime = examreporttime;
    }
}

