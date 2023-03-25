package Capstone.Bioproject.web.main;

import Capstone.Bioproject.web.domain.DiseaseRankInterface;
import Capstone.Bioproject.web.main.dto.MainResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final MainService mainService;
    @GetMapping("/main")
    public List<MainResponseDto> sendRank(){
        return mainService.getDiseaseRank();
    }
}
