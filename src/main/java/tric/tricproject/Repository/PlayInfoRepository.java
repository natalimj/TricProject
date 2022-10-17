package tric.tricproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.PlayInfo;


@Repository
public interface PlayInfoRepository  extends JpaRepository<PlayInfo, Long> {
}
