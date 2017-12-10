package tpdev.upmc.dcinephila.Beans;

/**
 * Created by Wissou on 09/12/2017.
 */

public class Actor {

    private int actor_id;
    private String actor_name;
    private String character;
    private String profile_picture;

    public Actor(int actor_id, String actor_name, String character, String profile_picture) {
        this.actor_id = actor_id;
        this.actor_name = actor_name;
        this.character = character;
        this.profile_picture = profile_picture;
    }

    public int getActor_id() {
        return actor_id;
    }

    public void setActor_id(int actor_id) {
        this.actor_id = actor_id;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
