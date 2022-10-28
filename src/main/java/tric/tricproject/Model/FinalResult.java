package tric.tricproject.Model;

public class FinalResult {

    private String category;
    private double rate;

    public FinalResult(String category, double rate) {
        this.category = category;
        this.rate = rate;
    }

    public FinalResult() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
