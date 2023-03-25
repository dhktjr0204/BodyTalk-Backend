package Capstone.Bioproject.web.main.dto;

import Capstone.Bioproject.web.domain.DiseaseRankInterface;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainResponseDto {
    private String name;
    private Float percent;

    @Builder
    public MainResponseDto(String name, Float percent) {
        this.name = name;
        this.percent = percent;
    }
    @Builder
    public MainResponseDto(DiseaseRankInterface diseaseRankInterface){
        this.name=diseaseRankInterface.getName();
        this.percent=diseaseRankInterface.getPercent();
    }
}
