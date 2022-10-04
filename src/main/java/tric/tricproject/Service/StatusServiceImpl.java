package tric.tricproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tric.tricproject.Model.Status;
import tric.tricproject.Repository.QuestionRepository;
import tric.tricproject.Repository.StatusRepository;

@Service
public class StatusServiceImpl implements StatusService{

    @Autowired
    StatusRepository statusRepository;

    @Override
    public Status setAppStatus(Boolean isActive) {
        Status status= new Status(1,isActive);
        return statusRepository.save(status);
    }

    @Override
    public Boolean getStatus() {
        return statusRepository.findById(1L).get().getIsActive();
    }
}
