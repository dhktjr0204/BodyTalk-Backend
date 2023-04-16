package Capstone.Bioproject.web.django.dto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DjangoResponseDto {
    private String disease;
    private String diseaseInfo;
    private String cause;
    private String type;
    private String date;

    @Builder
    public DjangoResponseDto(String disease, String diseaseInfo, String cause, String type, String date) {
        this.disease = disease;
        this.diseaseInfo = diseaseInfo;
        this.cause=cause;
        this.type=type;
        this.date=date;
    }
}
