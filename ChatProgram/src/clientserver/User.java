package clientserver;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private Icon profilePic;

    public User(String name, Icon profilePic) {
        this.name = name;
        this.profilePic = profilePic;
    }

    public User(String name) {
        this(name, null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePic(Icon profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public Icon getProfilePic() {
        return profilePic;
    }
}
