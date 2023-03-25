package Capstone.Bioproject.web.repository;
import Capstone.Bioproject.web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUser(User user);

    @Query(value = "SELECT symptom\n" +
            "FROM (SELECT * FROM diary INNER JOIN diarytag ON diary.id=diarytag.diary) AS B INNER JOIN tag ON B.tag= tag.id\n" +
            "WHERE date BETWEEN :stdate AND :eddate AND user_id= :id\n" +
            "GROUP BY tag\n" +
            "ORDER BY COUNT(tag) DESC\n" +
            "LIMIT 3",nativeQuery = true)
    List<SymptomRankInterface> findSymptomRank(@Param("id") Long user_id,
                                               @Param("stdate") String StartDate,
                                               @Param("eddate") String EndDate);

    @Query(value = "SELECT DATE AS date\n" +
            "FROM (SELECT * FROM diary INNER JOIN diarytag ON diary.id=diarytag.diary) AS B INNER JOIN tag ON B.tag= tag.id\n" +
            "WHERE date BETWEEN :stdate AND :eddate AND user_id= :id AND symptom= :symptom\n" +
            "ORDER BY DATE",nativeQuery = true)
    List<DateInterface> findSymtomDate(@Param("id") Long user_id,
                                       @Param("stdate") String StartDate,
                                       @Param("eddate") String EndDate,
                                       @Param("symptom") String symptom);

    @Query(value = "SELECT type, COUNT(TYPE) AS cnt\n" +
            "FROM (SELECT * FROM diary INNER JOIN diarytag ON diary.id=diarytag.diary) AS B INNER JOIN tag ON B.tag= tag.id\n" +
            "WHERE date BETWEEN :stdate AND :eddate AND user_id= :id\n" +
            "GROUP BY type",nativeQuery = true)
    List<TypeInterface> findType(@Param("id") Long user_id,
                                       @Param("stdate") String StartDate,
                                       @Param("eddate") String EndDate);
}
