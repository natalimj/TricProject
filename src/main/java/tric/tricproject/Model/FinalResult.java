package tric.tricproject.Model;

import java.util.List;

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
