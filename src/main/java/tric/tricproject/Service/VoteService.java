package tric.tricproject.Service;

import tric.tricproject.Model.PlayInfo;
import tric.tricproject.Model.Vote;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface
 * containing methods to perform CRUD operations for {@link Vote}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Service
public interface VoteService {
    public Vote addVote(Vote vote);
    public void deleteAllVotes();
}
