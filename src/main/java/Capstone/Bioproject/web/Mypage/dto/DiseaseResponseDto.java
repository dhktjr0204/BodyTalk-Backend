package Capstone.Bioproject.web.Mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseResponseDto {
    String content;
    String disease;
    String info;
    String cause;
    String type;
}
