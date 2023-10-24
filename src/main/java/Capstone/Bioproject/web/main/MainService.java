package Capstone.Bioproject.web.main;


import Capstone.Bioproject.web.domain.DiseaseRankInterface;
import Capstone.Bioproject.web.main.dto.MainResponseDto;
import Capstone.Bioproject.web.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MainService {
    private final ContentRepository contentRepository;
    @Transactional
    public List<MainResponseDto> getDiseaseRank() {
        List<DiseaseRankInterface> diseaseRank = contentRepository.findDiseaseRank();
        //AtomicInterger는 스트림 내부에서 안전하게 증가 시킬 수 있다.
        AtomicInteger sum_percent= new AtomicInteger();
        List<MainResponseDto> result = diseaseRank.stream()
                .map(rank -> {
                    sum_percent.addAndGet(rank.getPercent());
                    return MainResponseDto
                            .builder()
                            .name(rank.getName())
                            .percent(rank.getPercent()).build();
                })
                .collect(Collectors.toList());

        int etc_percent=100- sum_percent.get();
        result.add(MainResponseDto.builder().name("기타").percent(etc_percent).build());
        return result;
    }
}
