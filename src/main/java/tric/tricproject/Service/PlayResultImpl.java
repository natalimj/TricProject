package tric.tricproject.Service;

import tric.tricproject.Model.PlayResult;
import tric.tricproject.Repository.PlayResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlayResultImpl implements PlayResultService {

    @Autowired
    PlayResultRepository playResultRepository;
    @Override
    public List<PlayResult> getAllPlayResults() {
        return playResultRepository.findAll() ;
    }

    @Override
    public List<PlayResult> getPlayResultsByDate(Date date) {
        return null; //TODO: implement getPlayResultsByDate
    }

    @Override
    public void createPlayResults() {

        //TODO: create a real list of PlayResults for session -  for archive / excel exporter
        List<PlayResult> playResults =new ArrayList<>();

        Date date = new Date(); // today
        //get all votes

        playResultRepository.saveAll(playResults);

    }
}
