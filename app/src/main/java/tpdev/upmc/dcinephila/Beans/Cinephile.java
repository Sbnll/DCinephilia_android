package tpdev.upmc.dcinephila.Beans;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Wissou on 06/12/2017.
 */
@IgnoreExtraProperties
public class Cinephile {

    private String firstname;
    private String lastname;
    private String email;
    private String password;


    public Cinephile() {
    }

    public Cinephile(String firstname, String lastname, String email, String password) {

        this.firstname = firstname ;
        this.lastname = lastname ;
        this.email = email ;
        this.password = password;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
