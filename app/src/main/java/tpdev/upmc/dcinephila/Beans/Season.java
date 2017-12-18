package tpdev.upmc.dcinephila.Beans;

/**
 * Created by Wissou on 16/12/2017.
 */

public class Season {

    private int season_id;
    private int season_number;
    private int season_episodes_number;
    private String season_date;
    private String poster;

    public Season(int season_id, int season_number, int season_episodes_number, String season_date, String poster) {
        this.season_id = season_id;
        this.season_number = season_number;
        this.season_episodes_number = season_episodes_number;
        this.season_date = season_date;
        this.poster = poster;
    }

    public int getSeason_id() {
        return season_id;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public int getSeason_episodes_number() {
        return season_episodes_number;
    }

    public void setSeason_episodes_number(int season_episodes_number) {
        this.season_episodes_number = season_episodes_number;
    }

    public String getSeason_date() {
        return season_date;
    }

    public void setSeason_date(String season_date) {
        this.season_date = season_date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
