package tric.tricproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}