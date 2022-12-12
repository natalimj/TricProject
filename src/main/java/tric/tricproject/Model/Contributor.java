package tric.tricproject.Model;

import javax.persistence.*;

/**
 * Contributor class holds information about a contributor (Cast or Dev Team)
 * The class is annotated with @Entity and @Table annotations
 * in order to map the object with a database table using Spring Data JPA
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, September 2022
 */
@Entity
@Table(name="contributor",schema ="play")
public class Contributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contributorId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name="playInfoId")
    private PlayInfo playInfo;

    public Contributor(long contributorId, String name, String description, String type) {
        this.contributorId = contributorId;
        this.name = name;
        this.description = description;
        this.type = type;
    }


    public Contributor(long contributorId, String name, String description, String type, PlayInfo playInfo) {
        this.contributorId = contributorId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.playInfo = playInfo;
    }

    public Contributor(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Contributor() {
    }

    public long getContributorId() {
        return contributorId;
    }

    public void setContributorId(long contributorId) {
        this.contributorId = contributorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlayInfo(PlayInfo playInfo) {
        this.playInfo = playInfo;
    }
}
