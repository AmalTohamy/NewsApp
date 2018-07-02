package tohamy.amal.newsapp;

import android.support.v7.app.AppCompatActivity;

public class News extends AppCompatActivity {
    private String pillarName;
    private String newsText;
    private String date;
    private String url;

    public News(String pillarName, String newsText, String date, String url) {
        this.pillarName = pillarName;
        this.newsText = newsText;
        this.date = date;
        this.url = url;
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
}
