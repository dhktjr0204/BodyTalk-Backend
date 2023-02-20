package Capstone.Bioproject.web.repository;

import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByUser(User user);
}
