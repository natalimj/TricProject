package tric.tricproject.Service;

import tric.tricproject.Model.PlayResult;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface PlayResultService {
    List<PlayResult> getAllPlayResults();

    List<PlayResult> getPlayResultsByDate(Timestamp date);

    void createPlayResults();

    PlayResult addPlayResult(PlayResult playResult);
}
