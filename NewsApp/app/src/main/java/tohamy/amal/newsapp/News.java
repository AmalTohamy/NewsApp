package tohamy.amal.newsapp;

public class News {
    private String pillarName;
    private String newsText;
    private String date;
    private String url;
    private String authorName;

    public News(String pillarName, String newsText, String authorName, String date, String url) {
        this.pillarName = pillarName;
        this.newsText = newsText;
        this.date = date;
        this.url = url;
        this.authorName = authorName;
    }

    public String getPillarName() {
        return pillarName;
    }


    public String getNewsText() {
        return newsText;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthorName() {
        return authorName;
    }

}
