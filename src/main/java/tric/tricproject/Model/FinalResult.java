package tric.tricproject.Model;

import java.util.List;


/**
 * FinalResult class is created as a DTO
 * to transfer the final result for a user.
 * It holds a list of CategoryRateList to show final rates for each category
 * and a list of FinalCategory to show answer categories selected by the user for each question theme
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, November 2022
 */
public class FinalResult {

    private List<CategoryRate> categoryRateList;
    private List<FinalCategory> finalCategoryList;


    public FinalResult(List<CategoryRate> categoryRateList, List<FinalCategory> finalCategoryList) {
        this.categoryRateList = categoryRateList;
        this.finalCategoryList = finalCategoryList;
    }

    public List<CategoryRate> getCategoryRateList() {
        return categoryRateList;
    }

    public void setCategoryRateList(List<CategoryRate> categoryRateList) {
        this.categoryRateList = categoryRateList;
    }

    public List<FinalCategory> getFinalCategoryList() {
        return finalCategoryList;
    }

    public void setFinalCategoryList(List<FinalCategory> finalCategoryList) {
        this.finalCategoryList = finalCategoryList;
    }

}
