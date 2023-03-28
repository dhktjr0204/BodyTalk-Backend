package Capstone.Bioproject.web.Mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiseaseResponseDto {
    String content;
    String disease;
    String info;
    String cause;
    String type;

    @Builder
    public DiseaseResponseDto(String content, String disease, String info, String cause, String type){
        this.content=content;
        this.disease=disease;
        this.info=info;
        this.cause=cause;
        this.type = type;
    }
}
