package tric.tricproject.Service;

import tric.tricproject.Model.Question;
import tric.tricproject.Model.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionService {

    Question addQuestion(String questionText, String firstAnswerText, String secondAnswerText);
    void deleteQuestion(Question question);
    void deleteQuestionById(long questionId);
    Question editQuestion(long questionId,String questionText,String firstAnswer, String secondAnswer);
    List<Question> getAllQuestions();
    Question getQuestionByNumber(int questionNumber);
    void updateQuestionNumbers();
    Result getResult(long questionId);
    Question addQuestionTime(long questionId, int time);

    void deleteAllQuestions();
}
