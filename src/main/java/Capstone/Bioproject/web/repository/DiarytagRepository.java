package Capstone.Bioproject.web.repository;

import Capstone.Bioproject.web.domain.Diarytag;
import Capstone.Bioproject.web.domain.DiarytagPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiarytagRepository extends JpaRepository<Diarytag, DiarytagPK> {
    List<Diarytag> findByDiary(Long diaryId);
}
