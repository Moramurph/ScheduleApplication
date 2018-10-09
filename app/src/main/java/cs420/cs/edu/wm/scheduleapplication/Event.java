package cs420.cs.edu.wm.scheduleapplication;

class Event {

    private String title;
    private String date;
    private String description;
    private String url;
    private String image;


    private String info;


    protected Event (String t, String d, String ds, String u, String i) {
        this.title = t;
        this.date = d;
        this.description = ds;
        this.image = i;
        this.url = this.fixURL(u);


    }

    protected void initialize() {
        this.info = this.title + "      " + this.date + "\n" + this.description + "\n" + this.url;
    }

    protected String getInformation() {
        return this.info;
    }

    protected String getTitle() {
        return this.title;
    }

    protected String getImage() { return this.image; }

    protected  String getUrl() { return this.url; }

    private String fixURL(String u) {
        if (url != null)
            return "https://" + u;
        else
            return "";
    }
}
