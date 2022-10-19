package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.Category;
import tric.tricproject.Model.FinalResult;

import java.util.List;
@Service
public interface CategoryService {
    List<FinalResult> getFinalResults(long userId,List<Category> categories);
    List<Category> getAllCategories();
}
