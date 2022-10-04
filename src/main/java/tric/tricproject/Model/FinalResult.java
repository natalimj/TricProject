package tric.tricproject.Model;

public class FinalResult {

    private Category category;
    private double rate;

    public FinalResult(Category category, double rate) {
        this.category = category;
        this.rate = rate;
    }

    public FinalResult() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


}
