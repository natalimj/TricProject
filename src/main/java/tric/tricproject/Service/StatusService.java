package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.Status;

@Service
public interface StatusService {
    Status setAppStatus(Boolean isActive);
    Boolean getStatus();
}
