package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.PlayInfo;
import tric.tricproject.Model.Status;

@Service
public interface PlayInfoService {

    PlayInfo editPlayInfo(PlayInfo playInfo);
    PlayInfo getPlayInfo();

    PlayInfo setAppStatus(Boolean isActive);
    Boolean getStatus();
}
