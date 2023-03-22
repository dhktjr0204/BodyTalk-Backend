package Capstone.Bioproject.web.repository;
import Capstone.Bioproject.web.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findBySymptom(String tag);
}
