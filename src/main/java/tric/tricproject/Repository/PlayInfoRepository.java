package tric.tricproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.PlayInfo;

/**
 * Repository interface extends JpaRepository
 * to perform CRUD operations for {@link PlayInfo}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Repository
public interface PlayInfoRepository  extends JpaRepository<PlayInfo, Long> {
}
