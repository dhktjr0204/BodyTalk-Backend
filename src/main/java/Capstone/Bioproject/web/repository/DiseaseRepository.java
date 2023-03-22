package Capstone.Bioproject.web.repository;

import Capstone.Bioproject.web.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Disease findByName(String name);
}
