package tric.tricproject.Model;

import javax.persistence.*;


/**
 * User class holds information about a user - audience of the play
 * The class is annotated with @Entity and @Table annotations
 * in order to map the object with a database table using Spring Data JPA
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, September 2022
 */
@Entity
@Table(name="users",schema ="voting")
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
