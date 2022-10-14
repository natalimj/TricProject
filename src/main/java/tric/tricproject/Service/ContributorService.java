package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.Contributor;
import java.util.List;

@Service
public interface ContributorService {

    Contributor addContributor(Contributor contributor);
    List<Contributor> getAllContributors();
    Contributor editContributor(Contributor contributor);
    List<Contributor> getCast();
    List<Contributor> getDevTeam();
}
