package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.Question;

/**
 * Service interface
 * containing methods to generate prediction for a user
 *
 * @author Daria-Maria Popa
 * @version 1.0, November 2022
 */
@Service
public interface PredictionService {
    void generatePredictions(int numberOfQuestions);
    int getPredictionForUser(long userId);
    void clearPredictions();
    boolean werePredictionsGenerated();
    int getPredictedAnswer(long userId) ;
}
