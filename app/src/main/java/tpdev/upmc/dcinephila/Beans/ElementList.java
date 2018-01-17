package tpdev.upmc.dcinephila.Beans;

/**
 * Created by Sourour Bnll on 01/01/2018.
 */

public class ElementList {

    private String movie;
    private String type;
    private String url;
    private String name;
    private String date;
    private String id_list;
    private String email;

    public ElementList()
    {

    }
    public ElementList(String movie, String type, String url, String name,String date,String id_list,String email) {
        this.movie = movie;
        this.type = type;
        this.url = url;
        this.name = name;
        this.date=date;
        this.id_list=id_list;
        this.email=email;
    }

    public ElementList(String movie,String email){
        this.movie=movie;
        this.email=email;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_list() {
        return id_list;
    }

    public void setId_list(String id_list) {
        this.id_list = id_list;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
