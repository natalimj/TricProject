package tric.tricproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tric.tricproject.Model.User;
import tric.tricproject.Model.Vote;
import tric.tricproject.Repository.AnswerRepository;
import tric.tricproject.Repository.UserRepository;
import tric.tricproject.Repository.VoteRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weka.clusterers.HierarchicalClusterer;
import weka.core.*;

import static weka.clusterers.HierarchicalClusterer.TAGS_LINK_TYPE;

@Service
public class PredictionServiceImpl implements PredictionService {
    private HashMap<Long, Integer> userPredictions = new HashMap<>();

    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnswerRepository answerRepository;

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

        Instances dataset = loadForHierarchical(votes, numberOfQuestions);
        HierarchicalClusterer hc = new HierarchicalClusterer();
        hc.setLinkType(new SelectedTag(4, TAGS_LINK_TYPE));  // CENTROID
        hc.setNumClusters(2);

        try {
            hc.buildClusterer(dataset);
            for (int i = 0; i < dataset.size(); i++) {
                userPredictions.put(users.get(i).getUserId(), hc.clusterInstance(dataset.get(i)));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

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
}
