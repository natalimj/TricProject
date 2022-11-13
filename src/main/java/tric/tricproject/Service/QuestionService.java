package tric.tricproject.Service;

import tric.tricproject.Model.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface QuestionService {
    
    void deleteQuestion(Question question);
    void deleteQuestionById(long questionId);
    List<Question> getAllQuestions();
    Question getQuestionByNumber(int questionNumber);
    void updateQuestionNumbers();
    Result getResult(long questionId);
    Question addQuestionTime(long questionId, int time);
    String getResultListJson();
    void deleteAllQuestions();
    FinalResult getFinalResults(long userId) ;
    Question addQuestion(Question question);
    Question editQuestion(Question question);
    int getPredictedAnswer(long userId) ;
    int getNumberOfQuestions();
}
