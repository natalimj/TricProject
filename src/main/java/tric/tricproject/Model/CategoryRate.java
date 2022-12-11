package tric.tricproject.Model;


/**
 * CategoryRate class is created as a DTO
 * holding category, opposite category and their rates
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, November 2022
 */
public class CategoryRate {

    private String category;
    private String oppositeCategory;
    private double rate;

    public CategoryRate(String category, String oppositeCategory, double rate) {
        this.category = category;
        this.oppositeCategory = oppositeCategory;
        this.rate = rate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOppositeCategory() {
        return oppositeCategory;
    }

    public void setOppositeCategory(String oppositeCategory) {
        this.oppositeCategory = oppositeCategory;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
