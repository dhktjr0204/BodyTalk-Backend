package Capstone.Bioproject.web.main;


import Capstone.Bioproject.web.domain.DiseaseRankInterface;
import Capstone.Bioproject.web.main.dto.MainResponseDto;
import Capstone.Bioproject.web.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {
    private final ContentRepository contentRepository;
    @Transactional
    public List<MainResponseDto> getDiseaseRank() {
        List<DiseaseRankInterface> diseaseRank = contentRepository.findDiseaseRank();
        List<MainResponseDto> result=new ArrayList<>();
        Float sum_percent= Float.valueOf(0);
        for (DiseaseRankInterface i : diseaseRank){
            result.add(MainResponseDto.builder().name(i.getName()).percent(i.getPercent()).build());
            sum_percent-=i.getPercent();
        }
        Float etc_percent=100-sum_percent;
        result.add(MainResponseDto.builder().name("기타").percent(etc_percent).build());
        return result;
    }
}
