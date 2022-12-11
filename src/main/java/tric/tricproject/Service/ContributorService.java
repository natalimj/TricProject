package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.Contributor;
import tric.tricproject.Model.PlayInfo;

import java.util.List;

/**
 * Service interface
 * containing methods to perform CRUD operations for {@link Contributor}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Service
public interface ContributorService {
    Contributor addContributor(Contributor contributor);
    List<Contributor> getAllContributors();
    Contributor editContributor(Contributor contributor);
    List<Contributor> getCast();
    List<Contributor> getDevTeam();
    void deleteContributorById(Long contributorId);
}
