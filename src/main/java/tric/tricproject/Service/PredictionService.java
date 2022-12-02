package tric.tricproject.Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface PredictionService {
    void generatePredictions(int numberOfQuestions);
    int getPredictionForUser(long userId);
    void clearPredictions();
    boolean werePredictionsGenerated();
}
