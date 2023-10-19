package Capstone.Bioproject.web.main.dto;

import Capstone.Bioproject.web.domain.DiseaseRankInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainResponseDto {
    private String name;
    private int percent;
}
