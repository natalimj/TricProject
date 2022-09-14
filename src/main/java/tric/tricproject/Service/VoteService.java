package tric.tricproject.Service;

import tric.tricproject.Model.Vote;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VoteService {
    public Vote addVote(Vote vote);
    public void deleteAllVotes();

    public List<Vote> getAllVotes();
    //  public Vote getUserVote(int userId,int questionNumber); //questionId
}
