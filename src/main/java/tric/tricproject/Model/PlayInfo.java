package tric.tricproject.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayInfo class holds information about play info
 * including application status showing current status of the play : active or inactive
 * The class is annotated with @Entity and @Table annotations
 * in order to map the object with a database table using Spring Data JPA
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, September 2022
 */
@Entity
@Table(name="playInfo", schema ="play")
public class PlayInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playInfoId;

    @Column(name = "text", length = 700)
    private String playInfoText;

    @Column(name = "finalResultText", length = 500)
    private String finalResultText;

    @Column(name = "isActive")
    private Boolean isActive;

    @OneToMany(mappedBy="playInfo",cascade={CascadeType.ALL})
    private List<Contributor> contributors=   new ArrayList<>();

    public PlayInfo(long playInfoId, String playInfoText, String finalResultText) {
        this.playInfoId = playInfoId;
        this.playInfoText = playInfoText;
        this.finalResultText = finalResultText;
    }

    public PlayInfo(long playInfoId) {
        this.playInfoId = playInfoId;
    }

    public PlayInfo(long playInfoId, String playInfoText, String finalResultText, Boolean isActive, List<Contributor> contributors) {
        this.playInfoId = playInfoId;
        this.playInfoText = playInfoText;
        this.finalResultText = finalResultText;
        this.isActive = isActive;
        this.contributors = contributors;
    }

    public PlayInfo() {
    }

    public long getPlayInfoId() {
        return playInfoId;
    }

    public void setPlayInfoId(long playInfoId) {
        this.playInfoId = playInfoId;
    }

    public String getPlayInfoText() {
        return playInfoText;
    }

    public void setPlayInfoText(String playInfoText) {
        this.playInfoText = playInfoText;
    }

    public String getFinalResultText() {
        return finalResultText;
    }

    public void setFinalResultText(String finalResultText) {
        this.finalResultText = finalResultText;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }
}
