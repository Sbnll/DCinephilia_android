package tpdev.upmc.dcinephila.Beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wissou on 13/12/2017.
 */

public class TVshow {

    private int show_id;
    private String show_title;
    private String airing_date;
    private String synopsis;
    private String poster;
    private float vote_average;

    public TVshow(int show_id, String show_title, String airing_date, String poster, float vote_average) {
        this.show_id = show_id;
        this.show_title = show_title;
        this.airing_date = airing_date;
        this.poster = poster;
        this.vote_average = vote_average;
    }

    public int getShow_id() {
        return show_id;
    }

    public String getShow_title() {
        return show_title;
    }

    public String getAiring_date() {
        return airing_date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPoster() {
        return poster;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }

    public void setAiring_date(String airing_date) {
        this.airing_date = airing_date;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {

            date = formatter.parse(this.airing_date);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
