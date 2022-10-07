package tric.tricproject.Model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "imagePath")
    private String imagePath;
    public User(long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public User(long userId, String username, String imagePath) {
        this.userId = userId;
        this.username = username;
        this.imagePath = imagePath;
    }

    public User(String username) {
        this.username = username;
    }

    public User() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
