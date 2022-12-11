package tric.tricproject.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tric.tricproject.Model.Answer;
import tric.tricproject.Model.Question;
import tric.tricproject.Model.User;
import tric.tricproject.Model.Vote;
import tric.tricproject.Repository.AnswerRepository;
import tric.tricproject.Repository.QuestionRepository;
import tric.tricproject.Repository.UserRepository;
import tric.tricproject.Repository.VoteRepository;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import weka.clusterers.HierarchicalClusterer;
import weka.core.*;

import static tric.tricproject.Service.QuestionServiceImpl.*;
import static weka.clusterers.HierarchicalClusterer.TAGS_LINK_TYPE;

/**
 * Service implementation class
 * containing methods to generate prediction for a user
 *
 *
 * @author Daria Maria Popa
 * @version 1.0, November 2022
 */
@Service
public class PredictionServiceImpl implements PredictionService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private HashMap<Long, Integer> userPredictions = new HashMap<>();

    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    /**
     * This method clusters the users based on their previous vote answers using
     * an agglomerative hierarchical clustering model. The result represents
     * the predictions for the answer of the last vote.
     *
     * @param numberOfQuestions Represents the number of questions in the database. Used to calculate
     *                          the dimension of the ARFF dataset and votes matrix
     */
    @Override
    public void generatePredictions(int numberOfQuestions) {
        List<User> users = userRepository.findAll();
        int[][] votes = new int[users.size()][numberOfQuestions];
        for (int i = 0; i < users.size(); i++) {
            List<Vote> userVotes = voteRepository.findAllByUserId(users.get(i).getUserId());
            for (int j = 0; j < userVotes.size(); j++) {
                votes[i][j] = answerRepository.findByAnswerId(userVotes.get(j).getAnswerId()).getAnswerText().equalsIgnoreCase("Yes") ? 0 : 1;
            }
        }

        try {
            Instances dataset = loadForHierarchical(votes, numberOfQuestions);
            HierarchicalClusterer hc = new HierarchicalClusterer();
            hc.setLinkType(new SelectedTag(4, TAGS_LINK_TYPE));  // CENTROID LINK
            hc.setNumClusters(2);
            hc.buildClusterer(dataset);
            for (int i = 0; i < dataset.size(); i++) {
                userPredictions.put(users.get(i).getUserId(), hc.clusterInstance(dataset.get(i)));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * @param userId The user id for which the prediction is to be retrieved
     * @return Returns the prediction generated with the machine learning model or 0 by default.
     */
    @Override
    public int getPredictionForUser(long userId) {
        return userPredictions.getOrDefault(userId, 0);
    }

    @Override
    public void clearPredictions() {
        userPredictions.clear();
    }

    @Override
    public boolean werePredictionsGenerated() {
        return userPredictions.size() > 0;
    }

    /**
     * Private method used to load the previous votes matrix into an ARFF dataset as required
     * by the Weka library used to run machine learning models
     *
     * @param data The previous votes data from the users
     * @param numberOfQuestions The number of questions in the database. Used to set the size of the dataset.
     * @return An Instances object which contains the given data in an ARFF format.
     */
    private static Instances loadForHierarchical(int[][] data, int numberOfQuestions) {
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 1; i <= numberOfQuestions; i++) {
            attributes.add(new Attribute("Vote" + i));
        }
        Instances dataset = new Instances("Dataset", attributes, data.length);
        for (int[] datum : data) {
            Instance instance = new SparseInstance(numberOfQuestions);
            for (int i = 0; i < numberOfQuestions; i++) {
                instance.setValue(i, datum[i]);
            }
            dataset.add(instance);
        }
        return dataset;
    }

    /**
     * Generates the prediction for a user based on a manual implementation of weighted category sum
     * for the previous answers' categories.
     * @param userId The id of the user for which the prediction is to be generated
     * @return The generated label as either a 0 or a 1, representing either the first or the second answer.
     */
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

        Question finalQuestion = questionRepository.findByQuestionNumber(getNumberOfQuestions());
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
                Question secondToLastQuestion = questionRepository.findByQuestionNumber(getNumberOfQuestions()-1);
                if(secondToLastQuestion.getAnswers().get(0).getFirstCategory().equals(finalQuestion.getAnswers().get(0).getFirstCategory())){
                    return 0;
                }
                return 1;
            }
        }
    }
    private int getNumberOfQuestions() {
        Long number = questionRepository.count();
        return  number.intValue();
    }
}
