package clientserver;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable, Comparable {
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

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof User) {
            return name.equals(((User) obj).getName());
        }
        return false;
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof User) {
            User other = (User) o;
            return name.compareTo(other.getName());
        }
        return 0;
    }
}
