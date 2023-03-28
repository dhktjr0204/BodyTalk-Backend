package Capstone.Bioproject.web.diary.dto;

import Capstone.Bioproject.web.domain.Diary;
import Capstone.Bioproject.web.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryResponseDto {
    private Long id;
    private String content;
    private LocalDate date;
    List<String> tag;

    @Builder
    public DiaryResponseDto(Long id, String content,LocalDate date, List<String> tag){
        this.id=id;
        this.content=content;
        this.date=date;
        this.tag=tag;
    }
}
