package tric.tricproject.Service;

import tric.tricproject.Model.PlayResult;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface PlayResultService {
    public  List<PlayResult> getAllPlayResults();

    public  List<PlayResult> getPlayResultsByDate(Date date);

    public void createPlayResults();
}
