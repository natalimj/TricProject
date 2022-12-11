package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.PlayInfo;
import tric.tricproject.Model.Status;

/**
 * Service interface
 * containing methods to perform CRUD operations for {@link PlayInfo}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Service
public interface PlayInfoService {

    PlayInfo editPlayInfo(PlayInfo playInfo);
    PlayInfo getPlayInfo();
    PlayInfo setAppStatus(Boolean isActive);
    Boolean getStatus();
}
