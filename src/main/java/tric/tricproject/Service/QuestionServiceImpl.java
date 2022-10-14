package tric.tricproject.Service;

import com.google.gson.Gson;
import tric.tricproject.Model.*;
import tric.tricproject.Repository.AnswerRepository;
import tric.tricproject.Repository.QuestionRepository;
import tric.tricproject.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements  QuestionService{
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    VoteRepository voteRepository;
    @Override
    public Question addQuestion(String questionText, String firstAnswerText, String secondAnswerText) {

        List<Answer> answers =new ArrayList<>();
        Question newQuestion = new Question(questionText);
        newQuestion.setTime(30); // default time - 30 seconds
        Question question = questionRepository.save(newQuestion);

        Answer firstAnswer = new Answer(firstAnswerText, question);
        Answer secondAnswer = new Answer(secondAnswerText, question);
        answerRepository.save(firstAnswer);
        answerRepository.save(secondAnswer);
        answers.add(firstAnswer);
        answers.add(secondAnswer);
        updateQuestionNumbers();
        question = questionRepository.findByQuestionId(question.getQuestionId());
        question.setAnswers(answers);
        return  question;
    }

    @Override
    public void deleteQuestion(Question questionToDelete) {
        questionRepository.delete(questionToDelete);
        updateQuestionNumbers();
    }

    @Override
    public void deleteQuestionById(long questionId) {
        questionRepository.deleteById(questionId);
        updateQuestionNumbers();
    }

    @Override
    public Question editQuestion(long questionId,String questionText,String firstAnswer, String secondAnswer){
        Question question = questionRepository.findByQuestionId(questionId);
        question.setQuestionText(questionText);
        question.getAnswers().get(0).setAnswerText(firstAnswer);
        question.getAnswers().get(1).setAnswerText(secondAnswer);
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionByNumber(int questionNumber) {
        return questionRepository.findByQuestionNumber(questionNumber);
    }

    public Result getResult(long questionId){

        Question question = questionRepository.findByQuestionId(questionId);
        Answer answer1 =  question.getAnswers().get(0);
        Answer answer2 =  question.getAnswers().get(1);

        List<Vote> votes = voteRepository.findAllByQuestionId(questionId);
        Result result = new Result();
        result.setQuestion(question);
        result.setFirstAnswer(answer1);
        result.setSecondAnswer(answer2);
        int firstAnswerNumber = (int) votes.stream().filter(v->v.getAnswerId() == answer1.getAnswerId()).count();

        if(votes.size() != 0){
            result.setFirstAnswerRate((100*firstAnswerNumber)/votes.size());
            result.setSecondAnswerRate(100-result.getFirstAnswerRate());
        }

        return result;
    }

    @Override
    public Question addQuestionTime(long questionId, int time) {
       Question question = questionRepository.findByQuestionId(questionId);
       question.setTime(time);
       return questionRepository.save(question);
    }
    public String getResultListJson() {
        List<PlayResult> resultList = new ArrayList<>();
        for(Question question: getAllQuestions()){
            Result result = getResult(question.getQuestionId());
            resultList.add(new PlayResult(result.getQuestion().getQuestionNumber(),
                    result.getQuestion().getQuestionText(),
                    result.getFirstAnswer().getAnswerText(),
                    result.getFirstAnswerRate(),
                    result.getSecondAnswer().getAnswerText(),
                    result.getSecondAnswerRate()));
        }
        return new Gson().toJson(resultList);
    }
    @Override
    public void deleteAllQuestions() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
    }

    public void updateQuestionNumbers(){
        List<Question> questions = questionRepository.findAllByOrderByQuestionIdAsc();
        for(int i= 0;i<questions.size();i++){
            questions.get(i).setQuestionNumber(i+1);
            questionRepository.save(questions.get(i));
        }
    }
}
