package tric.tricproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tric.tricproject.Model.Contributor;
import tric.tricproject.Model.PlayInfo;
import tric.tricproject.Model.Status;
import tric.tricproject.Repository.PlayInfoRepository;

/**
 * Service implementation class
 * performing CRUD operations for {@link PlayInfo}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Service
public class PlayInfoServiceImpl implements PlayInfoService{

    @Autowired
    PlayInfoRepository playInfoRepository;

    @Override
    public PlayInfo editPlayInfo(PlayInfo playInfo) {
        return playInfoRepository.save(playInfo);
    }

    @Override
    public PlayInfo getPlayInfo() {
        return playInfoRepository.findById(1L).get();
    }

    @Override
    public PlayInfo setAppStatus(Boolean isActive) {
        PlayInfo playInfo = getPlayInfo();
        playInfo.setActive(isActive);
        return playInfoRepository.save(playInfo);
    }

    @Override
    public Boolean getStatus() {
        return playInfoRepository.findById(1L).get().isActive();
    }
}
