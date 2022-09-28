package tric.tricproject.Service;

import tric.tricproject.Model.Question;
import tric.tricproject.Model.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionService {

    public Question addQuestion(String questionText, String firstAnswerText, String secondAnswerText);
    public void deleteQuestion(Question question);
    public void deleteQuestionById(long questionId);
    public Question editQuestion(long questionId,String questionText,String firstAnswer, String secondAnswer);
    public List<Question> getAllQuestions();
    public Question getQuestionByNumber(int questionNumber);
    public void updateQuestionNumbers();
    public Result getResult(long questionId);

}
