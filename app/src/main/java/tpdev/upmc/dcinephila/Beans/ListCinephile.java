package tpdev.upmc.dcinephila.Beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourour Bnll on 18/12/2017.
 */

public class ListCinephile {

    private String uid;
    private String title;
    private String email;
    private List<Integer> elements;


    public ListCinephile() {
    }

    public ListCinephile(String uid, String title,String email) {
        this.uid = uid ;
        this.title = title;
        this.email=email;
        this.elements= new ArrayList<Integer>();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getElements() {
        return elements;
    }

    public void setElements(List<Integer> elements) {
        this.elements = elements;
    }
}
