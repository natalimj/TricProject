package tric.tricproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tric.tricproject.Model.PlayInfo;
import tric.tricproject.Repository.PlayInfoRepository;

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
}
