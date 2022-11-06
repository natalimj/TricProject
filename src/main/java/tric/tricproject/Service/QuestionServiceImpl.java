package tric.tricproject.Service;

import com.google.gson.Gson;
import tric.tricproject.Model.FinalCategory;
import tric.tricproject.Model.*;
import tric.tricproject.Repository.AnswerRepository;
import tric.tricproject.Repository.QuestionRepository;
import tric.tricproject.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    VoteRepository voteRepository;

    static final String CATEGORY1 = "Conservative";
    static final String CATEGORY2 = "Progressive";
    static final String CATEGORY3 = "Pragmatic";
    static final String CATEGORY4 = "Idealist";

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
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionByNumber(int questionNumber) {
        return questionRepository.findByQuestionNumber(questionNumber);
    }

    public Result getResult(long questionId) {

        Question question = questionRepository.findByQuestionId(questionId);
        Answer answer1 = question.getAnswers().get(0);
        Answer answer2 = question.getAnswers().get(1);

        List<Vote> votes = voteRepository.findAllByQuestionId(questionId);
        Result result = new Result();
        result.setQuestion(question);
        result.setFirstAnswer(answer1);
        result.setSecondAnswer(answer2);
        int firstAnswerNumber = (int) votes.stream().filter(v -> v.getAnswerId() == answer1.getAnswerId()).count();

        if (votes.size() != 0) {
            result.setFirstAnswerRate((100 * firstAnswerNumber) / votes.size());
            result.setSecondAnswerRate(100 - result.getFirstAnswerRate());
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
        for (Question question : getAllQuestions()) {
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

    @Override
    public FinalResult getFinalResults(long userId) {

        List<FinalCategory> finalCategories = new ArrayList<>();
        List<Vote> votes = voteRepository.findAllByUserId(userId);
        for (Vote vote : votes) {
            Question question = questionRepository.findByQuestionId(vote.getQuestionId());
            Answer answer = answerRepository.findByAnswerId(vote.getAnswerId());
            FinalCategory finalCategory = new FinalCategory(question.getTheme(), answer.getFirstCategory(),
                    answer.getSecondCategory());
            finalCategories.add(finalCategory);
        }

        List<String> primaryCategories = new ArrayList<>();
        List<String> secondaryCategories = new ArrayList<>();
        for (FinalCategory finalCategory : finalCategories) {
            primaryCategories.add(finalCategory.getAnswerFirstCategory());
            secondaryCategories.add(finalCategory.getAnswerSecondCategory());
        }

        double primaryCategory1 = Collections.frequency(primaryCategories, CATEGORY1);
        double primaryCategory3 = Collections.frequency(primaryCategories, CATEGORY3);

        double secondaryCategory1 = Collections.frequency(secondaryCategories, CATEGORY1);
        double secondaryCategory3 = Collections.frequency(secondaryCategories, CATEGORY3);

        double category1Rate = ((primaryCategory1 + secondaryCategory1) * 100) / votes.size();
        double category3Rate = ((primaryCategory3 + secondaryCategory3) * 100) / votes.size();

        List<CategoryRate> categoryRateList = new ArrayList<>();
        CategoryRate c1 = new CategoryRate(CATEGORY1, CATEGORY2, category1Rate);
        CategoryRate c2 = new CategoryRate(CATEGORY3, CATEGORY4, category3Rate);

        categoryRateList.add((c1));
        categoryRateList.add((c2));
        FinalResult finalResult = new FinalResult(categoryRateList, finalCategories);
        return finalResult;
    }

    @Override
    public Question addQuestion(Question _question) {

        List<Answer> answers = new ArrayList<>();
        Question question2 = new Question(_question.getQuestionText(), _question.getTime(), _question.getTheme());
        Answer firstAnswer = new Answer(_question.getAnswers().get(0).getAnswerText(),
                _question.getAnswers().get(0).getFirstCategory(), _question.getAnswers().get(0).getSecondCategory());
        Answer secondAnswer = new Answer(_question.getAnswers().get(1).getAnswerText(),
                _question.getAnswers().get(1).getFirstCategory(), _question.getAnswers().get(1).getSecondCategory());

        answers.add(firstAnswer);
        answers.add(secondAnswer);

        Question question = questionRepository.save(question2);
        updateQuestionNumbers();

        firstAnswer.setQuestion(question);
        secondAnswer.setQuestion(question);
        answerRepository.save(firstAnswer);
        answerRepository.save(secondAnswer);
        question = questionRepository.findByQuestionId(question.getQuestionId());
        question.setAnswers(answers);
        return question;
    }

    @Override
    public Question editQuestion(Question _question) {
        Question question = questionRepository.findByQuestionId(_question.getQuestionId());
        question.setQuestionText(_question.getQuestionText());
        question.setTheme(_question.getTheme());
        question.getAnswers().get(0).setAnswerText(_question.getAnswers().get(0).getAnswerText());
        question.getAnswers().get(1).setAnswerText(_question.getAnswers().get(1).getAnswerText());
        question.getAnswers().get(0).setFirstCategory(_question.getAnswers().get(0).getFirstCategory());
        question.getAnswers().get(0).setSecondCategory(_question.getAnswers().get(0).getSecondCategory());
        question.getAnswers().get(1).setFirstCategory(_question.getAnswers().get(1).getFirstCategory());
        question.getAnswers().get(1).setSecondCategory(_question.getAnswers().get(1).getSecondCategory());
        return questionRepository.save(question);
    }

    @Override
    public int getPredictedAnswer(long userId) {
        List<Vote> votes = voteRepository.findAllByUserId(userId);
        List<String> primaryCategories = new ArrayList<>();
        List<String> secondaryCategories = new ArrayList<>();

        for (Vote vote : votes) {
            Answer answer = answerRepository.findByAnswerId(vote.getAnswerId());
            primaryCategories.add(answer.getFirstCategory());
            secondaryCategories.add(answer.getSecondCategory());
        }

        HashMap<String, Double> weightedCategories = new HashMap<>();
        weightedCategories.put(CATEGORY1, Collections.frequency(primaryCategories, CATEGORY1) + (Collections.frequency(secondaryCategories, CATEGORY1) * 0.5));
        weightedCategories.put(CATEGORY2, Collections.frequency(primaryCategories, CATEGORY2) + (Collections.frequency(secondaryCategories, CATEGORY2) * 0.5));
        weightedCategories.put(CATEGORY3, Collections.frequency(primaryCategories, CATEGORY3) + (Collections.frequency(secondaryCategories, CATEGORY3) * 0.5));
        weightedCategories.put(CATEGORY4, Collections.frequency(primaryCategories, CATEGORY4) + (Collections.frequency(secondaryCategories, CATEGORY4) * 0.5));

        Question finalQuestion = questionRepository.findByQuestionNumber(getAllQuestions().size());
        if (weightedCategories.get(finalQuestion.getAnswers().get(0).getFirstCategory()) > weightedCategories.get(finalQuestion.getAnswers().get(1).getFirstCategory())) {
            return 0;
        } else if (weightedCategories.get(finalQuestion.getAnswers().get(0).getFirstCategory()) < weightedCategories.get(finalQuestion.getAnswers().get(1).getFirstCategory())) {
            return 1;
        } else {
            if (weightedCategories.get(finalQuestion.getAnswers().get(0).getSecondCategory()) > weightedCategories.get(finalQuestion.getAnswers().get(1).getSecondCategory())) {
                return 0;
            } else if (weightedCategories.get(finalQuestion.getAnswers().get(0).getSecondCategory()) < weightedCategories.get(finalQuestion.getAnswers().get(1).getSecondCategory())) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void updateQuestionNumbers() {
        List<Question> questions = questionRepository.findAllByOrderByQuestionIdAsc();
        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setQuestionNumber(i + 1);
            questionRepository.save(questions.get(i));
        }
    }
}
