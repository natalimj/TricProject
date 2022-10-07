package tric.tricproject.Model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "imagePath")
    private String imagePath;
    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(long id, String username, String imagePath) {
        this.id = id;
        this.username = username;
        this.imagePath = imagePath;
    }

    public User(String username) {
        this.username = username;
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
