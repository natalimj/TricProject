package tric.tricproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tric.tricproject.Model.Contributor;
import tric.tricproject.Repository.ContributorRepository;

import java.util.List;

/**
 * Service implementation class
 * performing CRUD operations for {@link Contributor}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Service
public class ContributorServiceImpl implements ContributorService {
    @Autowired
    ContributorRepository contributorRepository;

    @Override
    public Contributor addContributor(Contributor contributor) {
        return contributorRepository.save(contributor);
    }

    @Override
    public List<Contributor> getAllContributors() {
        return contributorRepository.findAll();
    }

    @Override
    public Contributor editContributor(Contributor contributor) {
        return contributorRepository.save(contributor);
    }

    @Override
    public List<Contributor> getCast() {
        return  contributorRepository.findAllByType("Cast");
    }

    @Override
    public List<Contributor> getDevTeam() {
        return  contributorRepository.findAllByType("Dev Team");
    }

    @Override
    public void deleteContributorById(Long contributorId) {
        contributorRepository.deleteById(contributorId);
    }
}
