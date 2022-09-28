package tric.tricproject.Service;

import tric.tricproject.Model.Answer;
import tric.tricproject.Model.Question;
import tric.tricproject.Model.Result;
import tric.tricproject.Model.Vote;
import tric.tricproject.Repository.AnswerRepository;
import tric.tricproject.Repository.QuestionRepository;
import tric.tricproject.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Question question = questionRepository.save(new Question(questionText));

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

        //TODO : Note: in frontend, don't save a question without two answers- show warning

        List<Vote> votes = voteRepository.findAllByQuestionId(questionId);
        Result result = new Result();
        result.setQuestion(question);
        result.setFirstAnswer(answer1);
        result.setSecondAnswer(answer2);
        int firstAnswerNumber = (int) votes.stream().filter(v->v.getAnswerId() == answer1.getAnswerId()).count();
        int secondAnswerNumber = (int) votes.stream().filter(v->v.getAnswerId() == answer2.getAnswerId()).count();

        System.out.println(firstAnswerNumber);
        System.out.println(secondAnswerNumber);
        if(votes.size() != 0){
            result.setFirstAnswerRate((100*firstAnswerNumber)/votes.size());
            result.setSecondAnswerRate((100*secondAnswerNumber)/votes.size());
        }

        return result;
    }

    public void updateQuestionNumbers(){
        List<Question> questions = questionRepository.findAllByOrderByQuestionIdAsc();
        for(int i= 0;i<questions.size();i++){
            questions.get(i).setQuestionNumber(i+1);
            questionRepository.save(questions.get(i));
        }
    }
}
