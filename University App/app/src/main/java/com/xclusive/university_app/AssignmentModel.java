package com.xclusive.university_app;

class AssignmentModel {
    private String papernamecode,assignmenttype,marks,obtainmark,pdflink,duedate,duetime,submitteddate,totalassignments,totalsubmited,submittedassignment;

    public AssignmentModel(String papernamecode, String assignmenttype, String marks,
                           String obtainmark, String pdflink, String duedate, String duetime,
                           String submitteddate, String totalassignments, String totalsubmited, String submittedassignment) {
        this.papernamecode = papernamecode;
        this.assignmenttype = assignmenttype;
        this.marks = marks;
        this.obtainmark = obtainmark;
        this.pdflink = pdflink;
        this.duedate = duedate;
        this.duetime = duetime;
        this.submitteddate = submitteddate;
        this.totalassignments = totalassignments;
        this.totalsubmited = totalsubmited;
        this.submittedassignment = submittedassignment;
    }

    public String getPapernamecode() {
        return papernamecode;
    }

    public void setPapernamecode(String papernamecode) {
        this.papernamecode = papernamecode;
    }

    public String getAssignmenttype() {
        return assignmenttype;
    }

    public void setAssignmenttype(String assignmenttype) {
        this.assignmenttype = assignmenttype;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getObtainmark() {
        return obtainmark;
    }

    public void setObtainmark(String obtainmark) {
        this.obtainmark = obtainmark;
    }

    public String getPdflink() {
        return pdflink;
    }

    public void setPdflink(String pdflink) {
        this.pdflink = pdflink;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getDuetime() {
        return duetime;
    }

    public void setDuetime(String duetime) {
        this.duetime = duetime;
    }

    public String getSubmitteddate() {
        return submitteddate;
    }

    public void setSubmitteddate(String submitteddate) {
        this.submitteddate = submitteddate;
    }

    public String getTotalassignments() {
        return totalassignments;
    }

    public void setTotalassignments(String totalassignments) {
        this.totalassignments = totalassignments;
    }

    public String getTotalsubmited() {
        return totalsubmited;
    }

    public void setTotalsubmited(String totalsubmited) {
        this.totalsubmited = totalsubmited;
    }

    public String getSubmittedassignment() {
        return submittedassignment;
    }

    public void setSubmittedassignment(String submittedassignment) {
        this.submittedassignment = submittedassignment;
    }
}
