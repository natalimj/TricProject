package tric.tricproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tric.tricproject.Model.Category;
import tric.tricproject.Model.FinalResult;
import tric.tricproject.Repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<FinalResult> getFinalResults(long userId, List<Category> categories) {
        List<FinalResult> finalResults = new ArrayList<>();

        //TODO: get real user input for the rate

        Random random = new Random();
        for (int i = 1; i < categories.size(); i=i+2) {
            finalResults.add(new FinalResult(categories.get(i), (random.nextInt(10)+1)*10));
        }
        return finalResults;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
