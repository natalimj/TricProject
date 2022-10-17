package tric.tricproject.Model;

import javax.persistence.*;

@Entity
@Table(name="contributor")
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

    public Contributor(long contributorId, String name, String description, String type) {
        this.contributorId = contributorId;
        this.name = name;
        this.description = description;
        this.type = type;
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
}
