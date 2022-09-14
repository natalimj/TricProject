package tric.tricproject.Service;

import tric.tricproject.Model.Vote;
import tric.tricproject.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService{
    @Autowired
    VoteRepository voteRepository;
    @Override
    public Vote  addVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public void deleteAllVotes() {
        voteRepository.deleteAll();
    }
    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }
}
