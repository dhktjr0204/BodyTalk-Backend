package Capstone.Bioproject.web.repository;

import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.DiseaseRankInterface;
import Capstone.Bioproject.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByUser(User user);

    @Query(value = "SELECT name,"+
            " ROUND((COUNT(disease)/(SELECT COUNT(disease) FROM content)*100)) AS percent\n" +
            "FROM content INNER JOIN disease ON content.disease=disease.disease_id\n" +
            "GROUP BY disease\n" +
            "ORDER BY COUNT(disease) DESC\n" +
            "LIMIT 5", nativeQuery = true)
    List<DiseaseRankInterface> findDiseaseRank();

}