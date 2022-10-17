package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Model.PlayInfo;

@Service
public interface PlayInfoService {

    PlayInfo editPlayInfo(PlayInfo playInfo);
    PlayInfo getPlayInfo();
}
