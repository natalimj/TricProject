package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.Category;
import tric.tricproject.Model.FinalResult;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<FinalResult> getFinalResults(long userId) {
        List<FinalResult> finalResults = new ArrayList<>();

        //TODO: create a real list of FinalResult for the user

        Category cat1 = new Category(1,"Category1");
        Category cat2 = new Category(2,"Category2",cat1);
        Category cat3 = new Category(3,"Category3");
        Category cat4 = new Category(4,"Category4",cat3);

        finalResults.add(new FinalResult(cat2,40));
        finalResults.add(new FinalResult(cat4,75));
        return finalResults;
    }
}
