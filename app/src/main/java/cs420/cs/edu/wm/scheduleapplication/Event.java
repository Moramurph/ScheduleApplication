package cs420.cs.edu.wm.scheduleapplication;

class Event {

    private String title;
    private String date;
    private String description;
    private String url;

    private String info;


    protected Event (String t, String d, String ds, String u) {
        this.title = t;
        this.date = d;
        this.description = ds;
        this.url = u;
    }

    protected void initialize() {
        this.info = this.title + "      " + this.date + "\n" + this.description + "\n" + this.url;
    }

    protected String getInformation() {
        return this.info;
    }

    protected  String getTitle() {
        return this.title;
    }
}
