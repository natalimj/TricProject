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

    @Column(name = "finalResultText")
    private String finalResultText;

    public PlayInfo(long playInfoId, String playInfoText, String finalResultText) {
        this.playInfoId = playInfoId;
        this.playInfoText = playInfoText;
        this.finalResultText = finalResultText;
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
}
