package Capstone.Bioproject.web.Mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor
public class ContentResponseDto {
    Long id;
    String content;
    String disease;
    LocalDate date;

    @Builder
    public ContentResponseDto(Long id, String content, String disease, LocalDate date){
        this.id=id;
        this.content=content;
        this.disease=disease;
        this.date=date;
    }
}
