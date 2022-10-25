package tric.tricproject.Service;

import tric.tricproject.Model.PlayResult;
import tric.tricproject.Model.Question;
import tric.tricproject.Model.Result;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface QuestionService {

    Question addQuestion(String questionText, String firstAnswerText, String secondAnswerText, List<Integer> firstAnswerCategories, List<Integer> secondAnswerCategories);
    void deleteQuestion(Question question);
    void deleteQuestionById(long questionId);
    Question editQuestion(long questionId,String questionText,String firstAnswer, String secondAnswer, List<Integer> firstAnswerCategories, List<Integer> secondAnswerCategories);
    List<Question> getAllQuestions();
    Question getQuestionByNumber(int questionNumber);
    Result getResult(long questionId);
    Question addQuestionTime(long questionId, int time);
    String getResultListJson();
    void deleteAllQuestions();
}
