package tric.tricproject.Model;

import javax.persistence.*;


@Entity
@Table(name="playInfo")
public class PlayInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playInfoId;

    @Column(name = "text")
    private String playInfoText;

    public PlayInfo(long playInfoId, String playInfoText) {
        this.playInfoId = playInfoId;
        this.playInfoText = playInfoText;
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
}
