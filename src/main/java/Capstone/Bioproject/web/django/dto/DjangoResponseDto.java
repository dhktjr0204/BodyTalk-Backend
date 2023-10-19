package Capstone.Bioproject.web.django.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DjangoResponseDto {
    private String disease;
    private String diseaseInfo;
    private String cause;
    private String type;
    private String date;
}
