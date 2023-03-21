package Capstone.Bioproject.web.domain.dtos;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DjangoResponseDto {
    private String disease;
    private String diseaseInfo;
    private List<String> hospital;

    @Builder
    public DjangoResponseDto(String disease, String diseaseInfo, List<String> hospital) {
        this.disease = disease;
        this.diseaseInfo = diseaseInfo;
        this.hospital = hospital;
    }
}
