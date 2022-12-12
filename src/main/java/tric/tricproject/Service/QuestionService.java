package tric.tricproject.Service;

import tric.tricproject.Model.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface
 * containing methods to perform CRUD operations for {@link Question}
 * and prepare the results and prediction to send to the client
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Service
public interface QuestionService {
    void deleteQuestionById(long questionId);
    List<Question> getAllQuestions();
    Question getQuestionByNumber(int questionNumber);
    Result getResult(long questionId);
    Question addQuestionTime(long questionId, int time);
    String getResultListJson();
    void deleteAllQuestions();
    FinalResult getFinalResults(long userId) ;
    Question addQuestion(Question question);
    Question editQuestion(Question question);
    int getNumberOfQuestions();
}
