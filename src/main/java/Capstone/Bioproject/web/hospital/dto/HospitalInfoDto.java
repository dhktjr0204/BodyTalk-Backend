package Capstone.Bioproject.web.hospital.dto;

import Capstone.Bioproject.web.domain.DiseaseRankInterface;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HospitalInfoDto {
    private String type;
    private Double lon;
    private Double lat;
}
