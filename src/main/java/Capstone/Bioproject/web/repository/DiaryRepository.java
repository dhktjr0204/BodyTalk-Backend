package Capstone.Bioproject.web.repository;


import Capstone.Bioproject.web.domain.Diary;
import Capstone.Bioproject.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUser(User user);
}
