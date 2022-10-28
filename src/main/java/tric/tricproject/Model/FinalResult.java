package tric.tricproject.Model;

public class FinalResult {

    private String category;

    private String secondCategory;
    private double rate;

    public FinalResult(String category, String secondCategory,double rate) {
        this.category = category;
        this.secondCategory = secondCategory;
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

    public String getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
