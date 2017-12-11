package tpdev.upmc.dcinephila.Beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Wissou on 07/12/2017.
 */

public class Movie {

    private int movie_id;
    private String movie_title;
    private String synopsis;
    private String movie_poster;
    private String release_date;
    private String movie_rate;

    public Movie() {
    }

    public Movie(int movie_id, String movie_title, String release_date, String movie_poster) {
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.release_date = release_date;
        this.movie_poster = movie_poster;
    }

    public Movie(int movie_id, String movie_title, String synopsis, String movie_poster, String release_date, String movie_rate) {
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.synopsis = synopsis;
        this.movie_poster = movie_poster;
        this.release_date = release_date;
        this.movie_rate = movie_rate;
    }

    public int getMovie_id() {
        return movie_id;
    }


    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getMovie_rate() {
        return movie_rate;
    }

    public void setMovie_rate(String movie_rate) {
        this.movie_rate = movie_rate;
    }

    public Date getDateTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {

            date = formatter.parse(this.release_date);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
