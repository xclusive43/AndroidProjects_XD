package com.xclusive.university_app;

class AnnouncementModel {
    private String announcetitle,announcebody,announcedate,announcefile;

    public AnnouncementModel(String announcetitle, String announcebody, String announcedate, String announcefile) {
        this.announcetitle = announcetitle;
        this.announcebody = announcebody;
        this.announcedate = announcedate;
        this.announcefile = announcefile;
    }

    public String getAnnouncetitle() {
        return announcetitle;
    }

    public void setAnnouncetitle(String announcetitle) {
        this.announcetitle = announcetitle;
    }

    public String getAnnouncebody() {
        return announcebody;
    }

    public void setAnnouncebody(String announcebody) {
        this.announcebody = announcebody;
    }

    public String getAnnouncedate() {
        return announcedate;
    }

    public void setAnnouncedate(String announcedate) {
        this.announcedate = announcedate;
    }

    public String getAnnouncefile() {
        return announcefile;
    }

    public void setAnnouncefile(String announcefile) {
        this.announcefile = announcefile;
    }
}
